### Order of Operations

Context Filters
TOP N, FIXED Level of Detail(LOD)
Dimension Filters
Data Blending
EXCLUDE and INCLUDE LOD

The order of operations means the order that an action is executed in Tableau. Tabluau users should be aware of the order of operations to avoid possible error. 
More detailed information can be found this link: https://onlinehelp.tableau.com/current/pro/desktop/en-us/order_of_operations.htm

For me, what is more important matter is that Tableau allows wrong results with minimum warning. For example, if we input wrong commands, we notice it by error message after running the commands. However, even when we mis-use the order of operations, Tableau creates a chart which looks normal. 

For example, if we want a chart result which shows sales by TOP 10 customers in New York City, we should apply the Dimension filter(New York City) as Context Filter after applying the TOP 10 filter. What matters is that Tableau shows a plausible chart (even though which has wrong data) when we do not apply the Context Filter. Thus, there is always a possibility for mistake, depending on Tableau users.

Likewise, Tableau's automatic default settings such as automatic aggregation and automatic join are advantages, but at the same time, which can be a cause of mistake. For example, Tableau automatically joins two datasets when there is at least one same named field. Also, the automatically created average or percentage in Tableau is the average or percentage out of total value. Thus, when there are different levels of dimensions, the value should not be confused with the sectoral average or percentage.  
