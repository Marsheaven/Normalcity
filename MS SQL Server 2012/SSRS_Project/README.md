### SSRS Project
#### Compared to SSIS work, creating a report is simple and easy. 
#### First, use SSRS Report Wizard and select data source as embedded dataset, writing SQL Query. Then, a tabular report is created. On the Design tab of the report, edit the report accordingly

#### Points
- To create a group, drag fields from the Report Data pane to the Grouping pane. 
  - Add parent/child group in the Grouping pane 
  - e.g. Add Year in the Column Grouping and add Monthe as a child group
  - For "drilldown", set the properties of the child group as to be toggled by its parent group
- To apply parameter filters, create new parameters(Year, MaxYear) using Query Designer in the dataset, in the Report Data pane.

  ```
  SELECT DISTINCT YEAR(OrderDate) [Year]
  FROM FactInternetSales
  ORDER BY YEAR(OrderDate) DESC
  ```
  ```
  SELECT YEAR(MAX(OrderDate)) [MaxYear]
  FROM FactInternetSales
  ```
  - Configure the parameter properties to be viewed by filtering
  - e.g. Year Parameter Properties: set the default values as MaxYear
  - Add Month Parameter as the same way


