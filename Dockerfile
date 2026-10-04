FROM maven:3.9-eclipse-temurin-8

# Install system dependencies
RUN apt-get update && apt-get install -y \
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

# Install Node.js 20+ (required for mermaid-cli)
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs && \
    rm -rf /var/lib/apt/lists/*

# Python dependencies
RUN pip install --break-system-packages matplotlib

# Node dependencies (global)
RUN npm install -g @mermaid-js/mermaid-cli yauzl

# Install bun (required for opencode)
RUN npm install -g bun

# Install opencode via official install script
RUN curl -fsSL https://opencode.ai/v2/install | bash

# Pre-install puppeteer browser (chrome-headless-shell)
ENV PUPPETEER_CACHE_DIR=/opt/puppeteer/cache
RUN mkdir -p /opt/puppeteer/cache && \
    npx puppeteer browsers install chrome-headless-shell --path=/opt/puppeteer/cache

# Set puppeteer executable path for mermaid-cli
ENV PUPPETEER_EXECUTABLE_PATH=/opt/puppeteer/cache/chrome-headless-shell/linux-148.0.7778.97/chrome-headless-shell-linux64/chrome-headless-shell
ENV PUPPETEER_CACHE_DIR=/opt/puppeteer/cache

WORKDIR /workspace

# Copy assets (charts and rendered diagrams) for thesis build
COPY doc/ /workspace/doc/

ENTRYPOINT ["bash"]