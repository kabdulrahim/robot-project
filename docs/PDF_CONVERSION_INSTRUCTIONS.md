# Converting Report to PDF

## Option 1: Using Visual Studio Code (Recommended)

1. Install the "Markdown PDF" extension in VS Code
2. Open `REPORT.md`
3. Press `Ctrl+Shift+P` (or `Cmd+Shift+P` on Mac)
4. Type "Markdown PDF: Export (pdf)"
5. Select it and the PDF will be generated

## Option 2: Using Pandoc (Command Line)

```bash
# Install pandoc first (if not installed)
# Windows: choco install pandoc
# Mac: brew install pandoc

# Convert to PDF
pandoc REPORT.md -o REPORT.pdf --pdf-engine=xelatex
```

## Option 3: Using Online Tools

1. Go to https://www.markdowntopdf.com/
2. Copy the contents of REPORT.md
3. Paste and download the PDF

## Option 4: Using Microsoft Word

1. Open REPORT.md in VS Code
2. Select All (Ctrl+A) and Copy (Ctrl+C)
3. Open Microsoft Word
4. Paste (Ctrl+V) - formatting may need adjustment
5. Save as PDF (File > Save As > PDF)

## Option 5: Using GitHub

1. Push the docs folder to GitHub
2. GitHub renders Markdown files nicely
3. Use browser's Print > Save as PDF feature

---

## Adding Screenshots to Report

After capturing screenshots:

1. Create a `screenshots` folder inside `docs`
2. Save screenshots as:
   - `01_startup.png`
   - `02_initialize.png`
   - `03_pen_down.png`
   - etc.

3. Update REPORT.md to include images:

```markdown
### 5.1 Program Startup
![Program Startup](screenshots/01_startup.png)
```

4. Then convert to PDF
