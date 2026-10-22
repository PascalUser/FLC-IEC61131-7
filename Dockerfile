FROM maven:3.9-eclipse-temurin-8

# 1. Install system dependencies (as ROOT)
RUN apt-get update && apt-get install -y \
    git \
    pandoc \
    python3 python3-pip \
    curl \
    unzip \
    libglib2.0-0 \
    libnss3 \
    libnspr4 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libdrm2 \
    libxkbcommon0 \
    libxcomposite1 \
    libxdamage1 \
    libxfixes3 \
    libxrandr2 \
    libgbm1 \
    libasound2t64 \
    && rm -rf /var/lib/apt/lists/*

# Install Node.js 20+
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs && \
    rm -rf /var/lib/apt/lists/*

# Global Python and Node dependencies (Removed opencode from here)
RUN pip install --break-system-packages matplotlib
RUN npm install -g @mermaid-js/mermaid-cli yauzl remark-cli remark-gfm bun

# Install OpenCode CLI via official install script (as ROOT)
RUN curl -fsSL https://opencode.ai/v2/install | bash

# 2. Create an unprivileged user (devuser)
RUN useradd -m -s /bin/bash devuser

# 3. Prepare directories and assign permissions to the new user
RUN mkdir -p /opt/puppeteer/cache /workspace && \
    chown -R devuser:devuser /opt/puppeteer /workspace

# 4. DROP PRIVILEGES: Everything from here on is executed as 'devuser'
USER devuser
WORKDIR /workspace

# Install Puppeteer locally in the user's cache
ENV PUPPETEER_CACHE_DIR=/opt/puppeteer/cache
RUN npx puppeteer browsers install chrome-headless-shell --path=/opt/puppeteer/cache

# Puppeteer paths
ENV PUPPETEER_EXECUTABLE_PATH=/opt/puppeteer/cache/chrome-headless-shell/linux-148.0.7778.97/chrome-headless-shell-linux64/chrome-headless-shell

# Copy assets (ensuring they belong to devuser)
COPY --chown=devuser:devuser doc/ /workspace/doc/

ENTRYPOINT ["bash"]