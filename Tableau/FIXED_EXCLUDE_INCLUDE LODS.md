### FIXED/EXCLUDE/INCLUDE Level of Detail (LOD)

The tableau automatically provides the aggregated sum (default aggregation) by a specific dimension when we select a dimension and a measure. The example 1 below shows the Sales by Category(Data Source: Superstore). This is the same value when the Fixed LOD is applied to Category. 

#### Example 1

![image](https://user-images.githubusercontent.com/37023565/49418749-b66fec00-f751-11e8-9167-622a4283bad8.png)

When there are multiple dimensions in the hierarchical relationships, Fixed LOD can be used to show its aggregated value though the default aggregation is applied to another dimension.

In the example 2, the default aggregation of Sales is applied by Sub-Category(lower-level). The Fixed-Category-Sales is the value after Fixed LOD is applied to Category(higher-level).

#### Example 2

![image](https://user-images.githubusercontent.com/37023565/49418761-bf60bd80-f751-11e8-9554-66c7bec0dd20.png)

In summary, Fixed LOD is a kind of filter functioning aggregation by a specific dimension. As Fixed LOD is prioritized over Exclude/Include LODs, the result applied by Fixed LOD is not affected from adding other dimensions in the rows/columns.

Exclude LOD is used to exclude a (lower level of) dimension in the aggregation computation for the higher level. In the example 2, if Exclude LOD is applied to Sub-Category (lower level), the result value is the same as the Fixed-Category-Sales.

Include LOD is used to include a dimension in the aggregation computation, when it is cleared in the rows or columns.

Basically, the function of Fixed LOD and Include LOD is the same in that both apply aggregation by a specific dimension. Also, Fixed LOD can be used instead of Exclude LOD as shown in the Example 2. Because excluding a lower level of dimension is the same as adding a fixed higher level of dimension in the aggregation. 
