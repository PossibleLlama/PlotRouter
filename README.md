# PlotRouter

## Overview

| Repo                             | Master Status                               | Code Coverage                                        |
| -------------------------------- | ------------------------------------------- | ---------------------------------------------------- |
| [UI][UI-Github]                  | [![CircleCI][UI-CCI-Badge]][UI-CCI]         | [![codecov][UI-CodeCov-Badge]][UI-CodeCov]           |
| [Plot Points][PP-Github]         | [![CircleCI][PP-CCI-Badge]][PP-CCI]         | [![codecov][PP-CodeCov-Badge]][PP-CodeCov]           |

[<img alt="Trello" src="https://upload.wikimedia.org/wikipedia/en/8/8c/Trello_logo.svg" width=70x/>][Trello]

[UI-Github]: https://github.com/PossibleLlama/PlotRouter-UI
[UI-CCI-Badge]: https://circleci.com/gh/PossibleLlama/PlotRouter-UI.svg?style=svg&circle-token=e65c69d2f59335eaf44e72d6487c478c518ab5b1
[UI-CCI]: https://circleci.com/gh/PossibleLlama/PlotRouter-UI/tree/master
[UI-CodeCov-Badge]: https://codecov.io/gh/PossibleLlama/PlotRouter-UI/branch/master/graph/badge.svg?token=nnGEN1vSks
[UI-CodeCov]: https://codecov.io/gh/PossibleLlama/PlotRouter-UI

[PP-Github]: https://github.com/PossibleLlama/PlotRouter-PP
[PP-CCI-Badge]: https://circleci.com/gh/PossibleLlama/PlotRouter-PP/tree/master.svg?style=svg&circle-token=07292fe316715bf5c78e72534a2d05a59c0e7880
[PP-CCI]: https://circleci.com/gh/PossibleLlama/PlotRouter-PP/tree/master
[PP-CodeCov-Badge]: https://codecov.io/gh/PossibleLlama/PlotRouter-PP/branch/master/graph/badge.svg?token=ZrFA9xeliL
[PP-CodeCov]: https://codecov.io/gh/PossibleLlama/PlotRouter-PP

[Trello]: https://trello.com/b/mEtQHsTR/plot-router

### Aim

This application aims to assist people in creation of stories, whether that is for a book, game, role playing, or anything else.

It hopes to make it easier to keep track of characters, what events are happening and in which order, and what dependencies these have to help you orchestrate your story.

### Misc

pr is shorthand for PlotRouter. This shorthand is used in naming conventions to quickly identify which elements belong to this project.

## Docker

The app can be ran via Docker, with docker-compose included.

As such you can run `docker-compose up pr-ui-consumer`, and everything required for the consumers ui will be created.

## Web App

[React](https://reactjs.org/) has been used to create the web application. There will be a consumer and an administrator ui, served by seperate api's.

To run locally, after cloning the repo you will need to run several commands.

``` bash
cd ui/consumer
npm i
npm t
npm start
```

`npm install` will download all required packages.
Start will allow you to access the application locally at `http://localhost:3000/`.

Run tests `npm t`. This will watch for any changes, and run the tests when any are detected. Either pressing `q` or `ctrl + c` will exit.

Compile and minify for production `npm run build`.

## Properties files

### Application

Currently only used to change the default port that the application is created at.

### Database

Holds mongo connection details. This is done at startup.

Accepts uri with all details filled in, or each field on another line. These two methods cannot be used at the same time.
