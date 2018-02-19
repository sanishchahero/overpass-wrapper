# Overpass-Wrapper library
[![Build Status](https://travis-ci.org/wolfhardfehre/overpass-wrapper.svg?branch=master)](https://travis-ci.org/wolfhardfehre/overpass-wrapper)

## Introduction

A Java wrapper library around the [Overpass QL][0] to query the [Overpass API][1]. Overpass is used 
to query physical [OpenStreetMap (OSM)][2] [features][3] on the ground (e.g., roads or buildings) 
using [tags][4] attached to its basic data structures ([nodes,][5] [ways,][6] [relations][7]). Each 
tag describes a geographic attribute of the feature being shown by that specific node, way or 
relation.

The building blocks of the query language are **statements** and have imperative semantics, meaning 
all statements are processed one after another and change the execution state according to their 
semantics. The **execution state** consists of the default set, potentially other named sets, basic 
structures, block statements, standalone queries and request/response settings.

## Similar Projects
Other overpass wrappers you should definitely look into...

1) [Overpasser by zsoltk](https://github.com/zsoltk/overpasser)
2) [Overpass by johnjohndoe](https://github.com/johnjohndoe/Overpass)

## Basic Statements

The structure hierarchy is **nodes > ways > relations**. Only nodes contain geometries.

+ **[Basic structures][8]**
    + **NodeQuery** equivalent to nodes - simple point geometries as smallest unit
    + **WayQuery** equivalent to way - line or polygon geometries build of nodes
    + **RelationQuery** equivalent to relation - multigeometry structure describing relationships
    + **ComplexQuery** possible mix of setting, recursion, set, node, way or relation 

### Other Statements

+ **[Standalone Queries:][9]** These are complete statements on their own
    + **Recursions** can be used to additionally select base structures up or down the hierarchy
    + **Sets** are created as result sets of statements and are read by subsequent statements as input. A set can contain nodes, ways, relations and areas, also of mixed type and of any number. 

+ **[Filters:][10]** They are always part of a query statement and contain the interesting selectors and filters.
    + **SearchRegion** allows to filter by **Type**: *BOUNDING_BOX*, *AROUND*, *POLYGON*, *AREA*
    + **Tags** allows to filter by attribute **Filter**: *EQUAL*, *NOT_EQUAL*, *IS*, *IS_NOT*, *LIKE*, *NOT_LIKE*
    + **Selection** inside **BaseQuery** allows to filter by recurse up or down the structure hierarchy: *FORWARD_NODE*, *FORWARD_WAY*, *FORWARD_RELATION*, *BACKWARD_NODE*, *BACKWARD_WAY*, *BACKWARD_RELATION* 
    
+ **[Block Statements:][11]** They group statements and enable disjunctions as well as loops.
    + in **ComplexQuery** you'll find combined operations: *union*, *difference* and *intersection*

+ **[Settings:][12]** Things like output format that can be set once.
    + **Settings** describes request conditions: *timeout*, *maxsize*, *global bounding box* 
    + **Modifier** are single additional information that can be written to each element: *BOUNDING_BOX*, *CENTER*, *GEOM*, *COUNT*  
    + **Verbosity** levels of response: *BODY*, *SKELETON*, *META*, *IDS*, *TAGS*

## Examples

[Here][13] you find a list of API endpoints and 
[here][14] a list of possible map features.

#### Post Box Example

Get all postal boxes around the position (latitude: 52.5, longitude: 13.4) in a 500 m radius.

**Conditions**
+ Server side query timeout at 25 seconds
+ Within a radius of 500 meter of a position
+ Case sensitive equality search

**Overpass QL** 
```groovy
[out:json][timeout:25];
node["amenity"="post_box"](around:500.0,52.5,13.4);
out;
```

**Overpass Wrapper**
```java
NodeQuery node = new NodeQuery.Builder()
        .timeout(25)
        .tag("amenity", "post_box")
        .around(52.5, 13.4, 500.0)
        .build();

Call<OverpassResponse> call = Overpass.ask(node);
```
> **Note:** Response format is always JSON
 
> **Note:** Default timeout is 10 seconds

> **Note:** Default tag operator is always equals

#### ILike Alexanderplatz Example

Make Auto-complete Address Search like with GooglePlacesAPI 
taking user position into account limit to 10 results.

**Conditions**
+ Case insensitive regex text search
+ Within a 1000 meter radius of a position
+ Limited to the best 10 results
+ Ascending order by distance

**Overpass QL**
```groovy
[out:json][timeout:10];
node[name~"alex", i](around:1000.0,52.521884,13.413181);
out 10 qt;
```
**Overpass Wrapper**
```java
NodeQuery node = new NodeQuery.Builder()
        .ilike("name", "alex")
        .around(52.521884, 13.413181, 1000.0)
        .sort()
        .limit(10)
        .build();

Call<OverpassResponse> call = Overpass.ask(node);
```
 
#### Public transport Route
Get all routes containing a specific station.

**Conditions**

+ Case insensitive regex and equility text search
+ Recurse up to get relations by node
+ Get geometries of ways and node within relation

**Overpass QL**

```groovy
[out:json];
(node["name"~"U Boddinstraße", i]
["public_transport"="stop_position"]
["railway"="stop"];
<;);out geom;
```

**Overpass Wrapper**

```java
NodeQuery node = new NodeQuery.Builder()
        .ilike("name", "U Boddinstraße")
        .equal("public_transport", "stop_position")
        .equal("railway", "stop")
        .build();

ComplexQuery complex = new ComplexQuery.Builder()
        .union(node, Recursion.UP)
        .modifier(Modifier.GEOM)
        .build();

Call<OverpassResponse> call = Overpass.ask(node);
```

## Dependencies

* Retrofit2
* OkHttp3
* Moshi

## Tests

Run the following command to execute all tests:

```bash 
    $ ./gradlew clean test 
```

## Author

* [Wolfhard Fehre][wolfhard]

## License

    Copyright 2018 Wolfhard Fehre

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[wolfhard]: https://github.com/wolfhardfehre

[0]: https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL
[1]: https://wiki.openstreetmap.org/wiki/Overpass_API
[2]: https://www.openstreetmap.org
[3]: https://wiki.openstreetmap.org/wiki/Features
[4]: https://wiki.openstreetmap.org/wiki/Tags
[5]: https://wiki.openstreetmap.org/wiki/Node
[6]: https://wiki.openstreetmap.org/wiki/Way
[7]: https://wiki.openstreetmap.org/wiki/Relation
[8]: https://wiki.openstreetmap.org/wiki/Overpass_API/Language_Guide#Overpass_QL_Basics
[9]: https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Standalone_queries
[10]: https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Filters
[11]: https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Block_statements
[12]: https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Settings
[13]: https://wiki.openstreetmap.org/wiki/Overpass_API#Introduction
[14]: https://wiki.openstreetmap.org/wiki/Map_Features


