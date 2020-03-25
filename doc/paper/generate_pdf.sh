#!/bin/bash

rm -f *.aux
rm -f *.log
rm -f *.gz
rm -f *.toc
rm -f *.bcf
rm -f *.idx
rm -f *.xml
rm -f *.bbl
rm -f *.blg
rm -f *.out

sust_tex_file="Paper"

# generate indices/bibliographies:
pdflatex $sust_tex_file".tex"

# generate bibtex file:
bibtex $sust_tex_file".aux"

# compile PDF with TOC etc.:
pdflatex $sust_tex_file".tex"
pdflatex $sust_tex_file".tex"

