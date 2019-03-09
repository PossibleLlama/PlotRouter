# PlotRouter

## Overview
Master build status
<br/>
[![Build Status](https://semaphoreci.com/api/v1/possiblellama/plotrouter/branches/master/shields_badge.svg)](https://semaphoreci.com/possiblellama/plotrouter)

[Trello board](https://trello.com/b/mEtQHsTR/plot-router)

## Properties files
### Application
Currently only used to change the default port that the application is created at.<br/>

### Database
Holds mongo connection details. This is done at startup.<br/>Accepts uri with all details filled in, or each field on another line. These two methods cannot be used at the same time.

