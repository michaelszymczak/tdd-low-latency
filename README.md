#  JLBH presentation

Run tests using the following commands.

    mvn -U clean test
 

Sample output on Intel® Core™ i7-4770HQ CPU @ 2.20GHz × 8, Ubuntu 16.04 LTS
Your results will vary.

##  TODO:

- Add explicit no GC checks
- Parametrize expectations so that they're machine/env dependent to observe the diff,
not the absolute results (more useful as a quick feedback loop and more reliable) 
 

```

$ mvn -U clean test
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for com.example:my-app:jar:1.0-SNAPSHOT
[WARNING] 'build.plugins.plugin.version' for org.apache.maven.plugins:maven-compiler-plugin is missing. @ line 10, column 21
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building my-app 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[WARNING] The artifact org.apache.commons:commons-io:jar:1.3.2 has been relocated to commons-io:commons-io:jar:1.3.2
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ my-app ---
[INFO] Deleting /home/maikeru/workspace/sandbox/chroniclesandbox/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ my-app ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.2:compile (default-compile) @ my-app ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 4 source files to /home/maikeru/workspace/sandbox/chroniclesandbox/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ my-app ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /home/maikeru/workspace/sandbox/chroniclesandbox/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.2:testCompile (default-testCompile) @ my-app ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 3 source files to /home/maikeru/workspace/sandbox/chroniclesandbox/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.17:test (default-test) @ my-app ---
[INFO] Surefire report directory: /home/maikeru/workspace/sandbox/chroniclesandbox/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.michaelszymczak.jlbh.ValuationTest
13:10:57.367 [Thread-0] INFO  net.openhft.affinity.AffinityLock - No isolated CPUs found, so assuming CPUs 1 to 7 available.
13:10:57.419 [main] WARN  net.openhft.affinity.LockInventory - Unable to acquire lock on CPU -1 for thread Thread[main,5,main], trying to find another CPU
13:10:57.422 [main] INFO  net.openhft.affinity.AffinityLock - Assigning cpu 6 to Thread[main,5,main]
Warm up complete (50000 iterations took 0.555s)
-------------------------------- BENCHMARK RESULTS (RUN 1) --------------------------------------------------------
Run time: 5.0s
Correcting for co-ordinated:true
Target throughput:10000/s = 1 message every 100us
End to End: (50,000)                            50/90 99/99.9 99.99 - worst was 4.2 / 4.2  4.5 / 4.7  14 - 184
OS Jitter (2,670)                               50/90 99/99.9 99.99 - worst was 1.9 / 2.8  10.0 / 48  233 - 233
-------------------------------------------------------------------------------------------------------------------
-------------------------------- BENCHMARK RESULTS (RUN 2) --------------------------------------------------------
Run time: 5.0s
Correcting for co-ordinated:true
Target throughput:10000/s = 1 message every 100us
End to End: (50,000)                            50/90 99/99.9 99.99 - worst was 4.2 / 4.2  4.2 / 4.5  14 - 92
OS Jitter (2,524)                               50/90 99/99.9 99.99 - worst was 1.8 / 2.5  4.0 / 36  135 - 135
-------------------------------------------------------------------------------------------------------------------
13:11:07.437 [main] INFO  net.openhft.affinity.LockInventory - Releasing cpu 6 from Thread[main,5,main]
-------------------------------- SUMMARY (end to end)------------------------------------------------------------
Percentile   run1         run2      % Variation
50:             4.22         4.22         0.00
90:             4.22         4.22         0.00
99:             4.48         4.22         0.00
99.9:           4.74         4.48         0.00
99.99:         14.08        14.08         0.00
worst:        184.32        92.16         0.00
-------------------------------------------------------------------------------------------------------------------
Total runs 150000, totalSum 160.000000
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 11.201 sec - in com.michaelszymczak.jlbh.ValuationTest

Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 14.367 s
[INFO] Finished at: 2018-06-08T13:11:07+01:00
[INFO] Final Memory: 19M/222M
[INFO] ------------------------------------------------------------------------

```