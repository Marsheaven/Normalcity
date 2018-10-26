## SQL QUERY EXAMPLES

- JOIN
- WHERE
- ORDER BY/RANK
- GROUP BY (Aggregates)/HAVING/COUNT
- STRING FUNCTIONS, LIKE, COALESCE
- CONVERT
- CORRELATED SUBQUERIES 
- DERIVED TABLE
- UNION
- OVER
- VIEW
- PROCEDURE
- PIVOT, UNPIVOT

### 1. JOIN
#### Join 3 Tables
```
SELECT c.custid, c.companyname, o.orderid, o.orderdate, od.productid, od.qty
FROM Sales.Customers AS c 
JOIN Sales.Orders AS o ON c.custid = o.custid
JOIN Sales.OrderDetails od ON o.orderid = od.orderid;
```
#### Left Outer Join
```
SELECT e.empid ,e.lastname as empname,e.title,e.mgrid, m.lastname as mgrname
  FROM HR.Employees AS e
  LEFT OUTER JOIN HR.Employees AS m
  ON e.mgrid=m.empid;
```
### 2. WHERE
```
SELECT orderid, custid, orderdate
FROM Sales.Orders
WHERE orderdate > '20061231' AND orderdate < '20080101';
```
```
Select empid, year(orderdate) as orderyear, custid
from Sales.Orders
WHERE empid = 3 and year(orderdate) = '2006'
Order by custid;
```
### 3. ORDER BY/RANK
#### Order By to Sort Results
```
SELECT orderid, custid, YEAR(orderdate) AS orderyear
FROM Sales.Orders
ORDER BY orderyear DESC;
```
```
SELECT TOP(5) productid, productname, unitprice,
	RANK() OVER(ORDER BY unitprice DESC) AS rankbyprice
FROM Production.Products
ORDER BY rankbyprice;
```
### 4. GROUP BY (Aggregates)/HAVING/COUNT
```
SELECT empid, YEAR(orderdate) AS orderyear,
COUNT(custid) AS all_custs,
COUNT(DISTINCT custid) AS unique_custs
FROM Sales.Orders
GROUP BY empid, YEAR(orderdate);
```
```
SELECT productid, MAX(qty) AS largest_order
FROM Sales.OrderDetails
GROUP BY productid
Having max(qty) >= 100;
```
```
SELECT p.productid, COUNT(*) AS cnt
FROM Production.Products AS p
JOIN Sales.OrderDetails AS od
ON p.productid = od.productid
GROUP BY p.productid
HAVING COUNT(*) >= 10
ORDER BY cnt DESC;
```
### 5. STRING FUNCTIONS, LIKE, COALESCE
#### String Functions
```
SELECT SUBSTRING('Microsoft SQL Server',11,3);
SELECT LEFT('Microsoft SQL Server',9);
SELECT RIGHT('Microsoft SQL Server',6);
SELECT LEN('Microsoft SQL Server     ');
SELECT DATALENGTH('Microsoft SQL Server     ');
SELECT CHARINDEX('SQL','Microsoft SQL Server');
SELECT REPLACE('Microsoft SQL Server Denali','Denali','2012');
SELECT UPPER('Microsoft SQL Server');
SELECT LOWER('Microsoft SQL Server');
```
#### LIKE predicate
```
SELECT categoryid, categoryname, description
FROM Production.Categories
WHERE description LIKE 'Sweet%';
```
#### Coalesce Function
```
SELECT	custid, country, region, city, 
			country + ',' + COALESCE(region, ' ') + ', ' + city as location
FROM Sales.Customers;
```
### 6. CONVERT
```
SELECT  CONVERT(datetime, '20120212', 102) AS ANSI_style ;
SELECT CONVERT(CHAR(8), CURRENT_TIMESTAMP,112) AS ISO_style;
SELECT PARSE('01/02/2012' AS datetime2 USING 'en-US') AS parse_result; 
```
### 7. CORRELATED SUBQUERIES 
```
SELECT orderid, empid, orderdate
FROM Sales.Orders AS O1
WHERE orderdate =
	(SELECT MAX(orderdate)
	 FROM Sales.Orders AS O2
	 WHERE O2.empid = O1.empid)
ORDER BY empid, orderdate;
```
```
SELECT custid, ordermonth, qty
FROM Sales.Custorders AS outercustorders
WHERE qty =
	(SELECT MAX(qty)
		FROM Sales.CustOrders AS innercustorders
		WHERE innercustorders.custid =outercustorders.custid
	)
ORDER BY custid;
```
#### WHERE IN
```
SELECT custid, orderid
FROM Sales.orders
WHERE custid IN (
	SELECT custid
	FROM Sales.Customers
	WHERE country = N'Mexico');
```
#### EXIST
```
SELECT custid, companyname
FROM Sales.Customers AS c 
WHERE EXISTS (
	SELECT * 
	FROM Sales.Orders AS o
	WHERE c.custid=o.custid);
```

### 8. DERIVED TABLE
```
SELECT orderyear, cust_count
FROM  (
	SELECT  orderyear, COUNT(DISTINCT custid) AS cust_count
	FROM (
		SELECT YEAR(orderdate) AS orderyear ,custid
        FROM Sales.Orders) AS derived_table_1
	GROUP BY orderyear) AS derived_table_2
WHERE cust_count > 80;
```
```
SELECT orderyear, COUNT(DISTINCT custid) AS cust_count
	FROM (
		SELECT YEAR(orderdate) AS orderyear ,custid
        FROM Sales.Orders) AS derived_table_1
	GROUP BY orderyear
HAVING COUNT(DISTINCT custid) > 80;
```
### 9. UNION
```
SELECT country, region, city FROM HR.Employees
UNION ALL 
SELECT country, region, city FROM Sales.Customers;
```
```
SELECT empid, country, region, city FROM HR.Employees where country = 'uk' and city = 'london'
UNION ALL 
SELECT custid, country, region, city FROM Sales.Customers;
```
### 10. OVER
#### Rank products by price in descending order in each category (partitioned by category)
```
SELECT CatID, CatName, ProdName, UnitPrice,
	RANK() OVER(PARTITION BY CatID ORDER BY UnitPrice DESC) AS PriceRank
FROM Production.CategorizedProducts
ORDER BY CatID; 
```
#### Running total of quantity per product category (Display like a bank statement)
```
SELECT Category, Qty, Orderyear,
	SUM(Qty) OVER (
		PARTITION BY category
		ORDER BY orderyear
		ROWS BETWEEN UNBOUNDED PRECEDING
		AND CURRENT ROW) AS RunningQty  ------like bank account statement for each category.
FROM Sales.CategoryQtyYear;
```
### 11. VIEW
#### Create a View
```
CREATE VIEW Sales.OrdersByEmployeeYear
AS
    SELECT  emp.empid AS employee ,
            YEAR(ord.orderdate) AS orderyear ,
            SUM(od.qty * od.unitprice) AS totalsales
    FROM    HR.Employees AS emp
            JOIN Sales.Orders AS ord ON emp.empid = ord.empid
            JOIN Sales.OrderDetails AS od ON ord.orderid = od.orderid
    GROUP BY emp.empid ,
            YEAR(ord.orderdate)
GO
```
#### Use the View
```
SELECT employee, orderyear, totalsales
FROM Sales.OrdersByEmployeeYear
ORDER BY employee, orderyear;
```
#### Delete the View 
```
DROP VIEW Sales.OrdersByEmployeeYear;
```
### 12. PROCEDURE
#### Check if the procedure already exists
```
IF OBJECT_ID('Production.sp_ProductsbySuppliers','P') IS NOT NULL
	DROP PROC Production.sp_ProductsbySuppliers;
GO
```
#### Create a Procedure
```
CREATE PROCEDURE Production.sp_ProductsbySuppliers
(@supplierid AS INT)
AS
SELECT  productid,
        productname,
        categoryid,
        unitprice,
        discontinued
FROM Production.Products
WHERE   supplierid = @supplierid
ORDER BY productid;
GO
```
#### Test the Procedure
```
EXEC Production.sp_ProductsbySuppliers @supplierid = 2;
GO
```
#### List Stored Procedures
```
USE TSQL2012;
GO
EXEC sys.sp_stored_procedures;
```
### 13. PIVOT, UNPIVOT
#### Pivot
```
SELECT  Category, [2006],[2007],[2008]
FROM    ( SELECT  Category, Qty, Orderyear FROM Sales.CategoryQtyYear) AS D 
    PIVOT(SUM(QTY) FOR orderyear IN ([2006],[2007],[2008])) AS pvt
ORDER BY Category;
```
#### Unpivot
```
CREATE TABLE [Sales].[PivotedCategorySales](
	[Category] [nvarchar](15) NOT NULL,
	[2006] [int] NULL,
	[2007] [int] NULL,
	[2008] [int] NULL);
GO
```
```
SELECT category, qty, orderyear
FROM Sales.PivotedCategorySales
UNPIVOT(qty FOR orderyear IN([2006],[2007],[2008])) AS unpvt;
```
