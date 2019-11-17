# PlotRouter

## Overview
Master build status
<br/>
[![CircleCI](https://circleci.com/gh/PossibleLlama/PlotRouter/tree/master.svg?style=svg)](https://circleci.com/gh/PossibleLlama/PlotRouter/tree/master)

[Trello board](https://trello.com/b/mEtQHsTR/plot-router)

## Properties files
### Application
Currently only used to change the default port that the application is created at.<br/>

### Database
Holds mongo connection details. This is done at startup.<br/>Accepts uri with all details filled in, or each field on another line. These two methods cannot be used at the same time.

### Web App
[Vue JS](https://vuejs.org/) has been used to create the web application.
After downloading 'npm install' to download all required packages.
Compile and hot reload 'npm run serve'.
Run tests 'npm run test:unit'.
Run linter 'npm run lint'.
Compile and minify for production 'npm run build'.
