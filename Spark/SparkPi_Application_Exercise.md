## Spark Application
### Issue in the executing sbt package

I followed the instruction in the guide(Spark_Applicaiton_Guide.pdf) to run the first scala application,
however I failed at the step of creating the jar file.

```
[root@localhost SparkPi]# sbt package
Getting org.scala-sbt sbt 1.2.3  (this may take some time)...
downloading file:////root/.sbt/preloaded/org.scala-sbt/sbt/1.2.3/jars/sbt.jar ...
	[SUCCESSFUL ] org.scala-sbt#sbt;1.2.3!sbt.jar (4ms)
	...
	...
[warn] There may be incompatibilities among your library dependencies.
[warn] Run 'evicted' to see detailed eviction warnings
[info] Compiling 1 Scala source to /root/SparkPi/target/scala-2.10/classes ...
[info] Non-compiled module 'compiler-bridge_2.10' for Scala 2.10.4. Compiling...
[info]   Compilation completed in 24.915s.
[error] /root/SparkPi/src/main/scala/SparkPi.scala:15:1: not found: value overflow
[error] overflow
[error] ^
[error] one error found
[error] (Compile / compileIncremental) Compilation failed
[error] Total time: 87 s, completed Oct 6, 2018 6:47:26 PM
[root@localhost SparkPi]#
```
### My solution
#### Version modification?
At first, I thought that this issue was caused by the different version of Spark(My Spark version is "2.2.0") which I use. Thus, I changed the version as follows:
```
scalaVersion of "2.10.4" => scalaVersion of "2.11.8" 
```
#### Revising importing package repository
I focused on the error message of 'overflow' and there seemed something wrong in the execution of the math function. This is not about the code itself, so the issue should be in the package of scala.math.random. Some part of the repository might have been missed or modified over time. Therefore, I corrected the importing repository as follows:
```
 import scala.math.random => import scala.math._
```
Then, it was successfully executed, regardless of the scalaVersion (in both of "2.10.4" and "2.11.8")

The following is the result of re-running the application successfully.

#### 1. Open the SparkPi.scala
```
[root@localhost scala]# vim SparkPi.scala
```
#### 2. Modify the first math packages as follows:
```
import scala.math._
import org.apache.spark._
```
#### 3. The rest part is the same
```
/** Computes an approximation to pi */
object SparkPi {
def main(args: Array[String]) {
/** Create the SparkConf object */
val conf = new SparkConf().setAppName("Spark Pi")
/** Create the SparkContext */
val spark = new SparkContext(conf)
/** business logic to calculate Pi */
val slices = if (args.length > 0) args(0).toInt else 2
val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid
overflow
val count = spark.parallelize(1 until n, slices).map { i =>
val x = random * 2 - 1
val y = random * 2 - 1
if (x*x + y*y < 1) 1 else 0
}.reduce(_ + _)
/** Printing the value of Pi */
println("Pi is roughly " + 4.0 * count / n)
/** Stop the SparkContext */
spark.stop()
}
}
```
#### 4. Move to the SparkPi and Remove the previously loaded project and target directories
```
[root@localhost SparkPi]# rm -Rf project/
[root@localhost SparkPi]# rm -Rf target/
[root@localhost SparkPi]# ls
sparkpi.sbt  src
```
#### 5. Create the jar file
```
[root@localhost SparkPi]# sbt package
[info] Updated file /root/SparkPi/project/build.properties: set sbt.version to 1.2.3
[info] Loading project definition from /root/SparkPi/project
.....
.....
[info] Compiling 1 Scala source to /root/SparkPi/target/scala-2.11/classes ...
[info] Done compiling.
[info] Packaging /root/SparkPi/target/scala-2.11/sparkpi-project_2.11-1.0.jar ...
[info] Done packaging.
[success] Total time: 24 s, completed Oct 8, 2018 12:45:07 AM
[root@localhost SparkPi]# 
```
#### 6. Running the application by spark-submit
```
[root@localhost SparkPi]# $SPARK_HOME/bin/spark-submit \
> --class "SparkPi" \
> --master local[4] \
> target/scala-2.11/sparkpi-project_2.11-1.0.jar
```
#### 7. Results of running the application
```
.....
18/10/08 00:59:49 INFO DAGScheduler: Job 0 finished: reduce at SparkPi.scala:19, took 1.916906 s
Pi is roughly 3.14048
18/10/08 00:59:49 INFO SparkUI: Stopped Spark web UI at http://172.16.129.70:4041
18/10/08 00:59:49 INFO MapOutputTrackerMasterEndpoint: MapOutputTrackerMasterEndpoint stopped!
18/10/08 00:59:49 INFO MemoryStore: MemoryStore cleared
18/10/08 00:59:49 INFO BlockManager: BlockManager stopped
18/10/08 00:59:49 INFO BlockManagerMaster: BlockManagerMaster stopped
18/10/08 00:59:49 INFO OutputCommitCoordinator$OutputCommitCoordinatorEndpoint: OutputCommitCoordinator stopped!
18/10/08 00:59:49 INFO SparkContext: Successfully stopped SparkContext
18/10/08 00:59:49 INFO ShutdownHookManager: Shutdown hook called
18/10/08 00:59:49 INFO ShutdownHookManager: Deleting directory /tmp/spark-5122bf5f-f460-49cd-84cc-d0d5ed0164c8
[root@localhost SparkPi]#
```
We can see the result of this application at the message of "Pi is roughly 3.14048".










