# jobs

Sample ClojureScript application searching using external API and displaying results.

## Overview

The goal was to build basic elements of job board https://functional.works-hub.com/jobs within two hours.

There should be an input field where search term could be entered and after the search is triggered we should render results as job cards.


We want to have 3 column layout on desktop and a single column on mobile. Cards should be responsive and stretch to fill the width of the container.

Search functionality is provided by Algolia.

## Development

To get an interactive development environment run:

    lein fig:build

This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

	lein clean

To create a production build run:

	lein clean
	lein fig:min
