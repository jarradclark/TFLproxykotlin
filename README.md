# TFL Proxy Kotlin (Spring Boot)
This project is almost identical to my other project in this repo here: https://github.com/jarradclark/TFLProxySpring
The only real difference is the language used under the hood.

I built this for use on low power ESP devices to remove the overhead of dealing with the larger json objects and sorting.

**This is still a work in progress**

# Aims
- Provide the bare minimum of data required to recreate TFL bus stop screens
- Sort the results into arrival order
- Have the ability to change the stop being returned (my unique use case)
- Ability to rename stops and destinations to match smaller screen real-estate

## Endpoints
- allArrivals - Returns all upcoming arrivals for the currently configured stop in arrival order
- arrivals/{stop_id} - Returns the upcoming arrivals for a specific stop in arrival order
- changeCurrentStop/{stop_id} - Changes the current stored stop (used for devices without external interfaces)

## Functionality
- A default stop is set via the main configuration and will be reverted on restart or after a configured interval.
- If the current stop is changed it will automatically revert to the default after a configurable interval.
- The names of both destinations and stops can be adjusted via the configuration.

## Planned Changes
- [ ] Ability to store current stop per unique identifier
- [ ] Caching of recently retrieved TFL data
- [ ] Ability to disable the auto revert feature via configuration
