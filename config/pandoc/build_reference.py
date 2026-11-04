#!/usr/bin/env python3
"""
Builds reference.docx (the style template pandoc uses to produce .docx files),
starting from pandoc's default reference.docx.

Usage:  python3 build_reference.py
Output: reference.docx  (in the current folder)

To change colors, fonts, line spacing or margins, edit the CONFIG section and
run the script again. You can also open reference.docx in Word and tweak the
styles by hand (Home > Styles > Modify).
"""
import subprocess
from docx import Document
from docx.enum.style import WD_STYLE_TYPE
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import nsdecls, qn
from docx.shared import Cm, Pt, RGBColor

# ============================ CONFIG ============================
ACCENT = "1F3A5F"       # deep blue for headings and details
GRAY = "6B6B6B"         # gray for subtitles and captions
BODY_FONT = "Cambria"   # body typeface (serif)
HEAD_FONT = "Calibri"   # heading typeface (sans serif)
MONO_FONT = "Consolas"  # code
BODY_PT = 12
LINE = 1.5              # body line spacing (1.5 is typical for theses)
INDENT_CM = 1.25        # first-line indent
PAGE_W_CM, PAGE_H_CM = 21.0, 29.7          # A4
MARGINS_CM = dict(top=3.0, bottom=3.0, left=3.5, right=2.5)
LANG = "en-US"
OUT = "reference.docx"
# ================================================================

TEXT_W_TWIPS = int((PAGE_W_CM - MARGINS_CM["left"] - MARGINS_CM["right"]) * 567)


def rgb(h):
    return RGBColor.from_string(h)


def font(style, name=None, size=None, bold=None, italic=None, color=None):
    f = style.font
    if name:
        f.name = name
        rf = style.element.get_or_add_rPr().find(qn("w:rFonts"))
        for a in ("w:asciiTheme", "w:hAnsiTheme", "w:eastAsiaTheme", "w:cstheme"):
            if rf.get(qn(a)) is not None:
                del rf.attrib[qn(a)]
        rf.set(qn("w:eastAsia"), name)
        rf.set(qn("w:cs"), name)
    if size is not None:
        f.size = Pt(size)
    if bold is not None:
        f.bold = bold
    if italic is not None:
        f.italic = italic
    if color:
        f.color.rgb = rgb(color)


def para(style, align=None, before=None, after=None, line=None, first=None,
         left=None, right=None, keep_next=None, keep_lines=None, page_break=None):
    pf = style.paragraph_format
    if align is not None:
        pf.alignment = align
    if before is not None:
        pf.space_before = Pt(before)
    if after is not None:
        pf.space_after = Pt(after)
    if line is not None:
        pf.line_spacing = line
    if first is not None:
        pf.first_line_indent = Cm(first)
    if left is not None:
        pf.left_indent = Cm(left)
    if right is not None:
        pf.right_indent = Cm(right)
    if keep_next is not None:
        pf.keep_with_next = keep_next
    if keep_lines is not None:
        pf.keep_together = keep_lines
    if page_break is not None:
        pf.page_break_before = page_break


PPR_ORDER = ["pStyle", "keepNext", "keepLines", "pageBreakBefore", "framePr", "widowControl",
             "numPr", "suppressLineNumbers", "pBdr", "shd", "tabs", "suppressAutoHyphens",
             "kinsoku", "wordWrap", "overflowPunct", "topLinePunct", "autoSpaceDE",
             "autoSpaceDN", "bidi", "adjustRightInd", "snapToGrid", "spacing", "ind",
             "contextualSpacing", "mirrorIndents", "suppressOverlap", "jc", "textDirection",
             "textAlignment", "textboxTightWrap", "outlineLvl", "divId", "cnfStyle", "rPr",
             "sectPr", "pPrChange"]


def ppr_insert(style, element):
    """Insert a child into w:pPr respecting the element order required by the OOXML schema."""
    pPr = style.element.get_or_add_pPr()
    local = element.tag.split("}")[1]
    for old in pPr.findall(element.tag):
        pPr.remove(old)
    successors = ["w:" + t for t in PPR_ORDER[PPR_ORDER.index(local) + 1:]]
    pPr.insert_element_before(element, *successors)


def border(style, **sides):
    """border(style, bottom=(sz, space, color), left=(...))"""
    xml = "<w:pBdr %s>" % nsdecls("w")
    for side in ("top", "left", "bottom", "right"):
        if side in sides:
            sz, space, color = sides[side]
            xml += ('<w:%s w:val="single" w:sz="%d" w:space="%d" w:color="%s"/>'
                    % (side, sz, space, color))
    xml += "</w:pBdr>"
    ppr_insert(style, parse_xml(xml))


def shade(style, fill):
    ppr_insert(style, parse_xml(
        '<w:shd %s w:val="clear" w:color="auto" w:fill="%s"/>' % (nsdecls("w"), fill)))


def add_field(paragraph, instr, size=10, color=GRAY, name=HEAD_FONT):
    def run():
        r = paragraph.add_run()
        r.font.size = Pt(size)
        r.font.name = name
        r.font.color.rgb = rgb(color)
        return r
    r = run()
    c = OxmlElement("w:fldChar"); c.set(qn("w:fldCharType"), "begin"); r._r.append(c)
    r = run()
    t = OxmlElement("w:instrText"); t.set(qn("xml:space"), "preserve"); t.text = " %s " % instr
    r._r.append(t)
    r = run()
    c = OxmlElement("w:fldChar"); c.set(qn("w:fldCharType"), "separate"); r._r.append(c)
    r = run(); r.text = "1"
    r = run()
    c = OxmlElement("w:fldChar"); c.set(qn("w:fldCharType"), "end"); r._r.append(c)


def main():
    with open("_base.docx", "wb") as fh:
        subprocess.run(["pandoc", "--print-default-data-file", "reference.docx"],
                       stdout=fh, check=True)
    doc = Document("_base.docx")
    S = doc.styles
    # pandoc names styles "Heading 1" (python-docx looks them up as "heading 1"), so index by real name
    ST = {st.name: st for st in S}
    styles_el = S.element

    # ---------- default language ----------
    rpr_default = styles_el.find(qn("w:docDefaults")).find(qn("w:rPrDefault")).find(qn("w:rPr"))
    for old in rpr_default.findall(qn("w:lang")):
        rpr_default.remove(old)
    lang = OxmlElement("w:lang")
    lang.set(qn("w:val"), LANG); lang.set(qn("w:eastAsia"), LANG); lang.set(qn("w:bidi"), "ar-SA")
    rpr_default.append(lang)

    # ---------- body text ----------
    font(ST["Normal"], BODY_FONT, BODY_PT)
    para(ST["Normal"], before=0, after=0)

    font(ST["Body Text"], BODY_FONT, BODY_PT)
    para(ST["Body Text"], WD_ALIGN_PARAGRAPH.JUSTIFY, before=0, after=6, line=LINE, first=INDENT_CM)

    font(ST["First Paragraph"], BODY_FONT, BODY_PT)
    para(ST["First Paragraph"], WD_ALIGN_PARAGRAPH.JUSTIFY, before=0, after=6, line=LINE, first=0)

    para(ST["Compact"], WD_ALIGN_PARAGRAPH.LEFT, before=0, after=2, line=1.15, first=0)

    # ---------- title page ----------
    font(ST["Title"], HEAD_FONT, 30, bold=True, color=ACCENT)
    para(ST["Title"], WD_ALIGN_PARAGRAPH.CENTER, before=130, after=12, line=1.1, keep_next=True)
    border(ST["Title"], bottom=(12, 12, ACCENT))
    font(ST["Subtitle"], HEAD_FONT, 16, bold=False, italic=False, color=GRAY)
    para(ST["Subtitle"], WD_ALIGN_PARAGRAPH.CENTER, before=12, after=100, line=1.2)
    font(ST["Author"], BODY_FONT, 15, bold=False, color="000000")
    para(ST["Author"], WD_ALIGN_PARAGRAPH.CENTER, before=0, after=6)
    font(ST["Date"], BODY_FONT, 12, color=GRAY)
    para(ST["Date"], WD_ALIGN_PARAGRAPH.CENTER, before=0, after=0)

    font(ST["Abstract Title"], HEAD_FONT, 14, bold=True, color=ACCENT)
    para(ST["Abstract Title"], WD_ALIGN_PARAGRAPH.LEFT, before=12, after=6)
    font(ST["Abstract"], BODY_FONT, 11)
    para(ST["Abstract"], WD_ALIGN_PARAGRAPH.JUSTIFY, before=0, after=6, line=1.15, left=1, right=1)

    # ---------- headings ----------
    heads = {
        1: dict(size=22, before=0, after=18, italic=False),
        2: dict(size=16, before=20, after=8, italic=False),
        3: dict(size=13, before=16, after=6, italic=False),
        4: dict(size=12, before=12, after=4, italic=True),
        5: dict(size=11, before=10, after=4, italic=True),
        6: dict(size=11, before=10, after=4, italic=True),
    }
    for n, h in heads.items():
        st = ST["Heading %d" % n]
        font(st, HEAD_FONT, h["size"], bold=True, italic=h["italic"], color=ACCENT)
        para(st, WD_ALIGN_PARAGRAPH.LEFT, before=h["before"], after=h["after"], line=1.0,
             first=0, left=0, keep_next=True, keep_lines=True)
    para(ST["Heading 1"], page_break=True)           # each chapter starts on a new page
    border(ST["Heading 1"], bottom=(8, 6, ACCENT))   # thin rule under the title

    # Table of contents heading (not a Heading style, so it doesn't list itself)
    toc_h = ST["TOC Heading"]
    toc_h.base_style = ST["Normal"]
    font(toc_h, HEAD_FONT, 22, bold=True, color=ACCENT)
    para(toc_h, WD_ALIGN_PARAGRAPH.LEFT, before=0, after=18, line=1.0, first=0,
         keep_next=True, page_break=False)
    border(toc_h, bottom=(8, 6, ACCENT))

    # Table of contents entry styles (Word uses them when updating the TOC field)
    def toc_style(level, left_twips, bold, size, before):
        if any(s.style_id == "TOC%d" % level for s in S):
            return
        styles_el.append(parse_xml(
            '<w:style %s w:type="paragraph" w:styleId="TOC%d">'
            '<w:name w:val="toc %d"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/>'
            '<w:uiPriority w:val="39"/><w:unhideWhenUsed/>'
            '<w:pPr><w:tabs><w:tab w:val="right" w:leader="dot" w:pos="%d"/></w:tabs>'
            '<w:spacing w:before="%d" w:after="40" w:line="276" w:lineRule="auto"/>'
            '<w:ind w:left="%d"/></w:pPr>'
            '<w:rPr><w:rFonts w:ascii="%s" w:hAnsi="%s" w:cs="%s"/>%s<w:sz w:val="%d"/></w:rPr>'
            '</w:style>' % (nsdecls("w"), level, level, TEXT_W_TWIPS, before * 20, left_twips,
                            HEAD_FONT, HEAD_FONT, HEAD_FONT, "<w:b/>" if bold else "", size * 2)))
    toc_style(1, 0, True, 11, 8)
    toc_style(2, 284, False, 11, 2)
    toc_style(3, 567, False, 10, 0)

    # ---------- quotes, footnotes, bibliography ----------
    bt = ST["Block Text"]
    font(bt, BODY_FONT, 11, italic=True)
    para(bt, WD_ALIGN_PARAGRAPH.JUSTIFY, before=6, after=10, line=1.15, first=0, left=1.25, right=1.0)
    border(bt, left=(18, 10, ACCENT))

    ft = ST["Footnote Text"]
    font(ft, BODY_FONT, 10)
    para(ft, WD_ALIGN_PARAGRAPH.JUSTIFY, before=0, after=2, line=1.0, first=0)

    bib = ST["Bibliography"]
    font(bib, BODY_FONT, 11)
    para(bib, WD_ALIGN_PARAGRAPH.LEFT, before=0, after=6, line=1.15, left=INDENT_CM, first=-INDENT_CM)

    # ---------- figures and tables ----------
    para(ST["Figure"], WD_ALIGN_PARAGRAPH.CENTER, before=6, after=0, line=1.0, first=0)
    para(ST["Captioned Figure"], WD_ALIGN_PARAGRAPH.CENTER, before=10, after=0, line=1.0,
         first=0, keep_next=True)
    font(ST["Caption"], HEAD_FONT, 10, bold=False, italic=False, color="404040")
    para(ST["Image Caption"], WD_ALIGN_PARAGRAPH.CENTER, before=4, after=14, line=1.1, first=0, left=1, right=1)
    font(ST["Image Caption"], HEAD_FONT, 10, italic=False, color="404040")
    para(ST["Table Caption"], WD_ALIGN_PARAGRAPH.CENTER, before=12, after=4, line=1.1,
         first=0, keep_next=True)
    font(ST["Table Caption"], HEAD_FONT, 10, italic=False, color="404040")

    # "booktabs"-style table: rule on top, under the header row, and at the bottom
    tbl = ST["Table"].element
    for tag in ("w:tblPr", "w:tblStylePr"):
        for old in tbl.findall(qn(tag)):
            tbl.remove(old)
    tbl.append(parse_xml(
        '<w:tblPr %s><w:tblInd w:w="0" w:type="dxa"/>'
        '<w:tblBorders>'
        '<w:top w:val="single" w:sz="12" w:space="0" w:color="%s"/>'
        '<w:bottom w:val="single" w:sz="12" w:space="0" w:color="%s"/>'
        '</w:tblBorders>'
        '<w:tblCellMar><w:top w:w="60" w:type="dxa"/><w:left w:w="100" w:type="dxa"/>'
        '<w:bottom w:w="60" w:type="dxa"/><w:right w:w="100" w:type="dxa"/></w:tblCellMar>'
        '</w:tblPr>' % (nsdecls("w"), ACCENT, ACCENT)))
    tbl.append(parse_xml(
        '<w:tblStylePr %s w:type="firstRow">'
        '<w:rPr><w:b/><w:color w:val="%s"/></w:rPr>'
        '<w:tcPr><w:tcBorders><w:bottom w:val="single" w:sz="6" w:space="0" w:color="%s"/></w:tcBorders></w:tcPr>'
        '</w:tblStylePr>' % (nsdecls("w"), ACCENT, ACCENT)))

    # ---------- code ----------
    if not any(s.style_id == "SourceCode" for s in S):
        S.add_style("Source Code", WD_STYLE_TYPE.PARAGRAPH)
    ST["Source Code"] = next(x for x in S if x.style_id == "SourceCode")
    sc = ST["Source Code"]
    sc.base_style = ST["Normal"]
    font(sc, MONO_FONT, 9.5)
    para(sc, WD_ALIGN_PARAGRAPH.LEFT, before=6, after=8, line=1.0, first=0, left=0.3, right=0.3)
    shade(sc, "F4F5F7")
    border(sc, left=(18, 6, ACCENT))
    font(ST["Verbatim Char"], MONO_FONT, 10)

    # ---------- links ----------
    font(ST["Hyperlink"], color=ACCENT)

    # ---------- page: A4, margins, centered page number in the footer ----------
    sec = doc.sections[0]
    sec.page_width, sec.page_height = Cm(PAGE_W_CM), Cm(PAGE_H_CM)
    sec.top_margin, sec.bottom_margin = Cm(MARGINS_CM["top"]), Cm(MARGINS_CM["bottom"])
    sec.left_margin, sec.right_margin = Cm(MARGINS_CM["left"]), Cm(MARGINS_CM["right"])
    sec.header_distance = sec.footer_distance = Cm(1.25)
    sec.different_first_page_header_footer = True   # the title page has no page number
    footer = sec.footer
    footer.is_linked_to_previous = False
    p = footer.paragraphs[0]
    for r in list(p.runs):
        r._r.getparent().remove(r._r)
    p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    add_field(p, "PAGE")

    doc.save(OUT)
    print("Done:", OUT)


if __name__ == "__main__":
    main()
