-- thesis.lua — pandoc filter for .docx output
--
-- 1) IMAGES: no image may exceed the text width or the maximum height set
--    below (so it fits on the page together with its caption). Small images
--    are not enlarged. If you write {width=60%} in the Markdown it is
--    respected, but the image is still shrunk if it would be too tall. If you
--    set height=... the filter leaves that image alone (full manual control).
-- 2) TITLE PAGE + CONTENTS: after the title/author/date block it adds a page
--    break, a table of contents (Word TOC field) and lets the first chapter
--    start on a new page. Disable it with `toc-page: false` in the YAML header.

-- ===== CONFIG (must match the margins in reference.docx) =====
local TEXT_W_CM = 15.0   -- A4 21 cm - 3.5 cm left - 2.5 cm right
local MAX_H_CM  = 11.0   -- maximum image height (leaves room for the caption)
local DEFAULT_DPI = 96   -- pandoc assumes 96 if the image declares no resolution
-- ===========================================================================

local TEXT_W = TEXT_W_CM / 2.54 * 72   -- in points
local MAX_H  = MAX_H_CM  / 2.54 * 72

-- ---------- reading image dimensions (PNG, JPEG, GIF) ----------
local function png_info(d)
  if #d < 24 then return nil end
  local w, h = string.unpack(">I4I4", d, 17)
  local dpi
  local p = d:find("pHYs", 1, true)
  if p and #d >= p + 12 then
    local ppu, _, unit = string.unpack(">I4I4B", d, p + 4)
    if unit == 1 and ppu > 0 then dpi = ppu * 0.0254 end
  end
  return w, h, dpi
end

local function jpeg_info(d)
  local pos, dpi = 3, nil
  while pos < #d do
    if d:byte(pos) ~= 0xFF then pos = pos + 1
    else
      local m = d:byte(pos + 1)
      if m == nil then return nil end
      if m == 0xFF then
        pos = pos + 1
      elseif m == 0xD8 or m == 0x01 or (m >= 0xD0 and m <= 0xD7) then
        pos = pos + 2
      else
        local len = string.unpack(">I2", d, pos + 2)
        if m == 0xE0 and d:sub(pos + 4, pos + 8) == "JFIF\0" then
          local units, xd = string.unpack(">BI2", d, pos + 11)
          if xd > 0 then
            if units == 1 then dpi = xd elseif units == 2 then dpi = xd * 2.54 end
          end
        end
        if m >= 0xC0 and m <= 0xCF and m ~= 0xC4 and m ~= 0xC8 and m ~= 0xCC then
          local h, w = string.unpack(">I2I2", d, pos + 5)
          return w, h, dpi
        end
        pos = pos + 2 + len
      end
    end
  end
  return nil
end

local function gif_info(d)
  if #d < 10 then return nil end
  local w, h = string.unpack("<I2I2", d, 7)
  return w, h, nil
end

local function image_info(d)
  if d:sub(1, 8) == "\137PNG\r\n\26\n" then return png_info(d) end
  if d:sub(1, 2) == "\255\216" then return jpeg_info(d) end
  if d:sub(1, 3) == "GIF" then return gif_info(d) end
  return nil
end

-- ---------- converting pandoc units to points ----------
local function to_pt(s, dpi)
  local num, unit = s:match("^([%d%.]+)%s*(%%?%a*)$")
  num = tonumber(num)
  if not num then return nil end
  if unit == "%" then return num / 100 * TEXT_W
  elseif unit == "cm" then return num / 2.54 * 72
  elseif unit == "mm" then return num / 25.4 * 72
  elseif unit == "in" then return num * 72
  elseif unit == "pt" then return num
  elseif unit == "px" or unit == "" then return num * 72 / dpi
  end
  return nil
end

function Image(img)
  if img.attributes.height then return nil end        -- the user is in control
  local ok, _, data = pcall(pandoc.mediabag.fetch, img.src)
  if not ok or not data then return nil end
  local pw, ph, dpi = image_info(data)
  if not pw or pw == 0 then return nil end            -- SVG or another format: leave untouched
  dpi = dpi or DEFAULT_DPI

  local w
  if img.attributes.width then w = to_pt(img.attributes.width, dpi)
  else w = pw * 72 / dpi end
  if not w then return nil end

  local h = w * ph / pw
  local scale = 1
  if w > TEXT_W then scale = TEXT_W / w end
  if h * scale > MAX_H then scale = MAX_H / h end
  if scale < 1 then
    img.attributes.width = string.format("%.1fpt", w * scale)
    return img
  end
  return nil
end

-- ---------- title page + contents ----------
local PAGEBREAK = [[<w:p><w:r><w:br w:type="page"/></w:r></w:p>]]
local TOC_FIELD = [[<w:p><w:r><w:fldChar w:fldCharType="begin" w:dirty="true"/></w:r><w:r><w:instrText xml:space="preserve"> TOC \o "1-3" \h \z \u </w:instrText></w:r><w:r><w:fldChar w:fldCharType="separate"/></w:r><w:r><w:t>Right-click here and choose “Update Field” (or press F9) to generate the table of contents.</w:t></w:r><w:r><w:fldChar w:fldCharType="end"/></w:r></w:p>]]

function Pandoc(doc)
  local meta = doc.meta
  if not meta.title then return nil end
  if meta["toc-page"] ~= nil and pandoc.utils.stringify(meta["toc-page"]) == "false" then
    return nil
  end
  local toc_title = meta["toc-title"] and pandoc.utils.stringify(meta["toc-title"]) or "Contents"

  local blocks = pandoc.List()
  blocks:insert(pandoc.RawBlock("openxml", PAGEBREAK))
  blocks:insert(pandoc.Div({ pandoc.Para({ pandoc.Str(toc_title) }) },
                           pandoc.Attr("", {}, { ["custom-style"] = "TOC Heading" })))
  blocks:insert(pandoc.RawBlock("openxml", TOC_FIELD))
  blocks:extend(doc.blocks)
  doc.blocks = blocks
  return doc
end
