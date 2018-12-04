### Order of Operations

![test](https://user-images.githubusercontent.com/37023565/49418074-8d019100-f74e-11e8-87e8-8874823e7772.jpg)

The order of operations means the order that an action is executed in Tableau. Tabluau users should be aware of the order of operations to avoid possible error. 
More detailed information can be found this link: https://onlinehelp.tableau.com/current/pro/desktop/en-us/order_of_operations.htm

For me, what is more important matter is lack of warning when we mis-use the order of operations. For example, if we input wrong commands, we notice it by error message after running the commands. However, even when we mis-use the order of operations, Tableau creates a plausible chart which looks normal. 

For example, if we want a chart result which shows sales by TOP 10 customers in New York City, we should apply the Dimension filter(New York City) as Context Filter after applying the TOP 10 filter. What matters is that Tableau shows a plausible chart even when we do not apply the Context Filter. Thus, there is always a possibility for mistake, depending on Tableau users.

Likewise, Tableau's automatic default settings such as automatic aggregation and automatic join are advantages, but at the same time, which can be a cause of mistake. For example, Tableau automatically joins two datasets when there is at least one same named field. Also, the automatically created average or percentage in Tableau is the average or percentage out of total value. Thus, when there are different levels of dimensions(e.g. category, sub-category), the value should not be confused with the sectoral average or percentage. (The sectoral average/percentage can be calculated by using Fixed or Exclusive LOD.)  

Overall, well-prepared dataset is necessary for optimizing Tableau as a visualization tool, along with understanding of order of operations of Tableau.
