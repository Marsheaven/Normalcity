### SSIS Project
#### This project is to create a SSIS pipeline from Staging DB to DataWarehouse DB which loads newly updated data on a monthly basis. Part 1 can be an independant SSIS work, but it is a preparation stage for Part 2.

#### Part 1
- Create a Staging DB and DataWarehouse DB via "Execute SQL Task"
- Create two packages for Extract, Transform, and Load data to the Staging DB, using derived column
  - "Extract Customers"
  - "Extract Internet Sales"

#### Part 2
- Some correction on the existing packages
- Create packages to load newly inserted data to dimensions(Products, Geography, and Customers) in the DataWarehouse DB
- Create a package to transfer the Internet Sales data from Staing DB to Datawarehouse DB
- Run the packages

#### Points
- Use a Lookup Transformation to insert/update Dimension table data
- "Load Product" package
  - Each dimension should lookup for its parent considering structure of the DataWarehouse DB, except for the Product Category Dimension
(e.g. Product Subcategory Dimension should lookup data in its parent dimension, Product Category Dimension)
- "Load Geography" package
  - Use SCD(Slowly Changing Dimension) transformation
  - Classify columns as one of the fixed attributes, historical attributes, and changing attributes according to change frequency
- Assign CurrentTime variable and set the update time when the CurrentTime is Last Extracted time + 30


