# Catalog
## QuickStart
1. Include the library in your project
2. Create ORMini.yml in your resources folder, left the settings empty if you want to use the default settings
3. Create a xml file in your resources folder which will hold the sql commands.
```xml
<ORMini>
    <sqls>
        <!--The sql command will be placed at here-->
    </sqls>
</ORMini>
```
As an example, if you want to implement a query method in your java, you can write the sql command in the xml file like this:
```xml
<ORMini>
    <sqls>
        <query id="getUserList" pojo="User" collection="list">
            select * from user
        </query>
    </sqls>
</ORMini>
```
4. modify the ORMini.yml and adding xml path into your settings, for example:
```yaml
xmlPath:
  file:
    - name: "user"
      sqlPath: "/user.xml"
```
The sqlPath represent the relative path from the resources folder to the xml file
5. To use the sql command, create a session in your java code, and call the sql command by it. As an example

```java
import org.frostyheco.databse.Session;
import org.frostyheco.databse.SessionFactory;
import java.util.List;

class Example {
    public static void main(String[] args) {
        Session session = SessionFactory.create("user");
        List<User> res=session.query("getUserList");
    }
}
```
As above, we successfully get the user list from the database.
## Settings
User can customize the settings by modifying the ORMini.yml file. The settings currently support are as follows:
```yaml
settings:
  mapper:
    fieldMapMode: camel #exact,camel(default)
    defaultCollection: list #array,single,list(default)
    typeEnum:
      defaultEnumConvert: ordinal #name,one begin,ordinal(default)
      defaultEnumNullable: true #false,true(default)
```
Here's an explanation of the settings:
### fieldMapMode: 
"fieldMapMode" represent the way to map the field name from the database to the pojo. 

For default camel mode, the field name will be converted to camel case, like "user_name"(database) -> "userName"(java Object).

For exact mode, the field name will be exactly the same as the database, like "user_name"(database) -> "user_name"(java Object).

### defaultCollection:
"defaultCollection" represent the default collection type when the result is a collection.

For default list mode, the result will be a list. If the result is empty, the list will be length 0.

For array mode, the result will be an array. If the result is empty, the array will be length 0.

For single mode, the result will be a single object. If the result is empty, the object will be null. Mention that if the result is more than one, an exception will be thrown.

### defaultEnumConvert:
"defaultEnumConvert" represent the default way to convert the enum type.

For default ordinal mode, the corresponding field in database of java enum field will be considered stored as its ordinal number,
which the value will be converted to enum type following this rule.

For name mode, the corresponding field in database of java enum field will be considered stored as its name...

For one begin mode, the corresponding field in database of java enum field will be considered stored as ordinal+1

### defaultEnumNullable:
"defaultEnumNullable" represent that if the enum field is nullable.

## API
TODO: It will be added in the future
