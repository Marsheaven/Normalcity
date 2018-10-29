### SPARK ETL

Spark can be used as a pipeline for the simple ETL(Extract, Transform, and Load data into DataWarehouse). There are some video clips in Youtube claiming the elimination/substitution of ETL by Spark. I do not fully agree with that, however, it is clear that Spark is a very fast and convenient platform for large amount of data manipulation.

I tested the Spark ETL using STM GTFS files(available at http://www.stm.info/en/about/developers). I picked up only three files(trips.txt, frequencies.txt, and calendar_dates.txt) to add minimal enrichments by join query, and created a mini-sized version of trips2.txt(4 rows) and frequencies2(4 rows) for a test.

- My test design is as follows:

  - Extract
    // I downloaded the STM GTFS files and stored them in the HDFS(staging area). And I opened the files into DataFrame in Spark.
  - Transform
    // I executed SQL Join query to integrate the columns of trips2.txt with those of frequencies2.txt 
  - Load
    // I saved the result as a csv file on the local disk. (Or, you can save them in your database created in HIVE)

Next is the detailed steps for Spark ETL.

1. Create a staging directory on HDFS

e.g. [root@localhost ~]# hadoop fs -mkdir /user/hive/warehouse/stm/gtfs/staging/frequencies2

2. Extract the downloaded data from STM and store them into staging area 

e.g. [root@localhost ~]# hadoop fs -put Downloads/gtfs_stm/frequencies2.txt /user/hive/warehouse/stm/gtfs/staging/frquencies2

3. Read files on HDFS into DataFrame 

scala> val frequencies2 = spark.read.format("csv").option("header","true").
     | load("hdfs://localhost:9000//user/hive/warehouse/stm/gtfs/staging/frequencies2/frequncies2.txt")

// Then, a DataFrame is created as follows. In the readig process, the text file is converted into the csv file with Schema.
frequencies2: org.apache.spark.sql.DataFrame = [trip_id: string, start_time: string ... 2 more fields]

// We can check the schema of the frequencies2 DataFrame

scala> frequencies2.printSchema()
root
 |-- trip_id: string (nullable = true)
 |-- start_time: string (nullable = true)
 |-- end_time: string (nullable = true)
 |-- headway_secs: string (nullable = true)

Likewise, I created a DataFrame for trips2

4. Create an enriched_trip DF using SQL




5. Save DataFrame of enriched_trips as a csv file
