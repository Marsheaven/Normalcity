## SSAS Project
### This SSAS project is composed of two parts, multidimensional model and tabular model analysis. While the multidimensional model is based on the analysis by creating a cube with several dimensions, tabular model eables the Power Pivot analysis via connection to the excel.

### 1. Multidimensional model analysis

Open the SQL Server Data tool, and select Analysis Services Multimensional and Data Mining Project. Create a data source view and a cube with multiple dimensions. Then customize dimensions and add measures.

Points
- To customize dimensions, click Edit Dimension and go into Dimension Designer. Drag the fields to be added in the Data Source View to the Attributes pane and rename them. 
- Create attribute relationships, and hierarchies in dimensions
- Use MDX(Multidimensional Expressions) to create a calculated mesaure Profit. On the Caculation tab of the cube designer, create a new calculated member named as [Profit].
   - Configure Expression as follows:
    ```
    [Measures]-[Sales Amount - Internet Sales] - [Measures].[Total] Product Cost - Internet Sales]  
    ```
### 2. Tabular model analysis

Use SSAS tool and create a tabular model analysis project. In Excel, create a PivotTable and a PivotChart based on the connection to the Analysis Server.

Points
- Open the Excel, and click the PowerPivot tab - PowerPivot Window. In this window, all of the work including connection to the server,  importing and editing data are implemented.  
- In Excel, create a PowerPivot table and a chart
- After adding a slider, click PivotTable Connection and select other ramaining PivotTables to be applied to the PivotTable and the PivotChart.



