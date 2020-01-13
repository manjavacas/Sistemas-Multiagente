\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{estilo}
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{article}}
\PassOptionsToClass{a4paper, 12pt, oneside, openright}{article}
\ProcessOptions\relax
\LoadClass{article}

\makeatletter

\usepackage[T1]{fontenc}
\usepackage[utf8]{inputenc}
\usepackage[spanish]{babel}
\usepackage{times}

\usepackage[left=30mm, right=20mm, top=20mm, bottom=20mm]{geometry}

\usepackage[%
  colorlinks=true,
  linkcolor=black,
  citecolor=blue,
  urlcolor=blue]{hyperref}

\usepackage[pdftex]{graphicx}
\graphicspath{{images/}}

\usepackage{setspace}

%--- lists ---
\usepackage{enumitem}
\setlist[itemize,1]{label=\textbullet, topsep=1mm, parsep=0.5mm}
\setenumerate{label=(\arabic*), parsep=-1mm, topsep=3pt}

%--- section style ---
\usepackage{titlesec}

\titleformat{\section}
{\large\bfseries\sffamily}{\thesection.}{.3cm}{}

\titlespacing{\section}{0pt}{0pt}{-0.2cm}
\titlespacing{\subsection}{0pt}{0pt}{-0.2cm}

%--- page style ---
\newcommand{\UCLMhead}{
  \setlength{\unitlength}{1cm}
  \begin{picture}(0,0)
    \put(-2.5, -2.5){
      \includegraphics[height=2.8cm]{uclm.pdf}}
  \end{picture}
}

\newcommand{\ESIhead}{
  \setlength{\unitlength}{1cm}
  \begin{picture}(0,0)
    \put(-4,-2){
      \includegraphics[height=1.6cm]{esi.pdf}}
  \end{picture}
}

\newpagestyle{firstpage}[\small\sffamily]{
  \sethead{\UCLMhead{}} %
          {} %
          {\ESIhead{}}
}

\newpagestyle{main}[\small\sffamily]{
  \sethead{}{}{}
  \setfoot{}{\thepage{}}{}
}

\parindent = 1pt
\setstretch{1.0}
\renewcommand\contentsname{Índice de contenido}

%--- document metadata ---
\usepackage{etoolbox}

\newcommand{\@authoruno}{}
\newcommand{\@authordos}{}
\newcommand{\docauthor}[2]{%
      \renewcommand{\@authoruno}{#1}
      \renewcommand{\@authordos}{#2}
  }

\newcommand{\@docmonth}{}
\newcommand{\@docyear}{}
\newcommand{\docdate}[2]{%
  \renewcommand{\@docyear}{#1}
  \renewcommand{\@docmonth}{#2}
  }


\def\add_frontpage_content{
  \begin{center}
    \thispagestyle{firstpage}
    \Large \bfseries
    \setstretch{1.3}

    \vspace*{\stretch{4}}
    \includegraphics[width=3cm]{informatica.pdf}

    \vspace*{\stretch{3}}
    UNIVERSIDAD DE CASTILLA-LA MANCHA

    ESCUELA SUPERIOR DE INFORMÁTICA

    \vspace*{\stretch{3}}

    SISTEMAS MULTIAGENTE

    GRADO EN INGENIERÍA INFORMÁTICA

    \vspace*{\stretch{3}}

    {\LARGE \@title}
  \end{center}

  \vspace*{\stretch{4}}
  
  {\Large \@authoruno \\ \@authordos}

  \hfill
  {\large \@docmonth, \@docyear}
}

\renewcommand\maketitle{%
  \begin{titlepage}
    \null\pagestyle{empty}
    \add_frontpage_content
    \vfil\null
  \end{titlepage}
  \parskip = 3mm
}

\makeatother
