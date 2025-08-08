# General Transit Feed Specification (GTFS) Tool

## Overview
This project was a semester-long assignment focused on developing a basic transit mapping application. The objective was to create a program that allows users to upload text files containing data on stops, stop times, trips, and routes. The program then maps this data onto a real-world map, displaying when and where specific transit services will be available. Users can customize the display by numbering and color-coding routes, offering flexibility in how the information is visualized.

The application was developed entirely in Java, utilizing JavaFX for the front-end interface. This project served as an introduction to both SCRUM methodology and front-end development for Java applications. Additionally, it provided me with hands-on experience in input handling, file parsing, and building interactive user interfaces.

## Objective

This project delivers a comprehensive Java-based tool for visualizing public transit data using the General Transit Feed Specification (GTFS). Built with JavaFX for the frontend, it enables users to upload transit datasets (e.g., stops, routes, trips), visualize routes on a map, and customize styling through numbering and color-coding for enhanced clarity.

## Key Features

- **GTFS Data Import**: Upload and parse foundational GTFS components—stops, stop times, trips, and routes.
- **Visual Mapping**: Display transit routes on a geographic interface, providing spatial and temporal views of transit operations.
- **Customization Options**:
  - Assign visual identifiers to routes (numbers, colors).
  - Tailor map appearance for user clarity and analysis.
- **Built with JavaFX**: Employs modern Java UI frameworks to deliver an intuitive and responsive desktop experience.
- **Agile Development Exposure**: Developed through Scrum-based semester workflows, offering experience in iterative feature development and collaboration.

## Architecture

1. **Data Model**:
   - Core representations: `Stop`, `Route`, `Trip`, `StopTime`, and higher-level `TransitMap`.
2. **UI Layer**:
   - Implemented via JavaFX with FXML-based views and controllers.
   - Controllers manage event handling and data flow from model to view.
3. **Interaction Flow**:
   - User loads GTFS files → model processes data → UI renders map overlays and route visuals.


## Requirements

- **Java 14+**
- **JavaFX** (latest compatible version)
- Run directly from `Model.java`; no compiled JAR provided.

## Usage Experience

- Load valid GTFS datasets.
- Interact with the GUI to view transit routes.
- Apply route numbering and coloration for visually organized mapping.

---
