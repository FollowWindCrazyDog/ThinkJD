# 1 ���(Project)

`ThinkJD`������`ThinkJDBC`��һ������ǿ��Ŀ�ԴJDBC�����⡣�����ʹ��Java��`ThinkPHP`��ܵ�M����һ�����������ݿ⡣

`ThinkJD`, also known as `ThinkJDBC`, an easy and powerful open source JDBC library. You can operate the database with JAVA just like the M method of `ThinkPHP` framework.

**��Ŀ��ҳ(Home Page)** https://github.com/Leytton/ThinkJD https://gitee.com/Leytton/ThinkJD

**������ҳ(Blog Page)** https://blog.csdn.net/Leytton

~~`����!!! æ��͵��һ��ʱ�������Ķ�������������ȫ����ԣ����油�ϣ���ӭ�����issue`~~

`�𲽲����У��������Ǿ������Ե��޶��޹��������ʳ��:)` 

������Ŀ(Test Demo) https://github.com/Leytton/ThinkJD_Demo

~~`Warning!!! This is just a one-day work as well as this document what you read.Too busy to do many`~~ 
~~`tests,more functions are expected to be found and welcome to have a overall test for it :)`~~

# 2 ʹ�÷���(Get Started)

## 0x01 �������(Add the Dependencies)
��[ThinkJDBC-x.x.x.jar](https://github.com/Leytton/ThinkJD/releases)�������������������ӵ���Ŀ����·���

Add ThinkJDBC-x.x.x.jar and the  following dependencies to the build path.

 - mysql-connector-java-5.1.39.jar
 - commons-dbutils-1.6.jar

 ������Jar��������Ŀ¼��(you can find them in the lib dir)��
 
 https://github.com/Leytton/ThinkJD/tree/master/lib
 
## 0x02 �������ݿ�(Config the Database)
ThinkJD֧��ֱ�Ӷ����û�������������ݿ⣬Ҳ֧��ʹ��Hikari��C3P0�����ݿ����ӳء�

There are two ways to connect database by using ThinkJD.You can config username and password or using the JDBC DataSources/Resource Pools  such as Hikari,C3P0,etc.

**���ȶ������ݿ����ӷ�ʽ��**

Firstly��you should define the way toconnect database:
### (1)�ʺ�����������ݿ�(Using Username and Password)
```
D.setDbConfig("jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=UTF-8","root","root");
```

### (2)ʹ�����ݿ����ӳ�(Using JDBC Pool)
����ʹ��Hikari���ӳ�(Example for Hikari)�� 
```
HikariConfig config = new HikariConfig("/hikari.properties");
HikariDataSource dataSource = new HikariDataSource(config);
D.setDataSource(dataSource);
```
`ע��������������ݿ����ӳأ�ThinkJD������ʹ�á�`

`Note that if you defined the JDBC pool,it will be preferred to use.`

### (3)���ñ�ǰ׺

```
D.setTablePrefix("jd_");
```

## 0x03 ���˷���(Filter Method)
| ����(Operation)| ����(Param)| ʾ��(Eg.) |˵��(Note) | 
| ------------- |------------- |------------- | -------------
|`table`|table(String table) | table("user") | 
|`join` |join(String join)| join("left join machine on user.id=user_id and machine_status=1")|
|`field`|��field(String filed)<br>��field(String filed, Object... dataParam)| ��field("id,username")<br>��field("id,username",1111,"Leytton")| �����ڲ�ѯ����(for select sql)<br>�����ڸ��²���(for update sql)
|`where`|��where(String where)<br>��where(String where, Object... whereParam)|��where("id=1111 and username='Leytton'")<br>��where("id=? and username=?",1111,"Leytton")
|`group`|group(String group)|group("type")
|`having`|having(String having)|having("id>1234")
|`order`|order(String order)|order("id desc")
|`page`|page(long page, long rows)|page(1,10)
|`limit`|��limit(long rows)<br>��limit(long offset, long rows)|��limit(10)<br>��limit(1,10)
|`union`|union(String union,Boolean isAll)|��union("select from user_two where id>1234",false)<br>��union("select from user_two where id>1234",true)

## 0x04 ��ѯ����(select method)

| ����(Operation)| ����(Param)| ˵��(Note) 
| -------- |--------|--------
|select|<`T`> List<`T`> select(Class<`T`> type)
|find|��<`T`> T find(Class<`T`> type)<br>��<`T`> T find(Class<`T`> type, long id)<br>��<`T`> T find(Class<`T`> type, String key, Object value)
|count|��long count()<br>��long count(String field)
|max|double max(String field)
|min|double min(String field)
|avg|double avg(String field)
|sum|double sum(String field)

```
//select id,name,weight from jd_user where id>3
List<User> res = new M("user").field("id,name,weight").where("id>3").select(User.class);

//select sex,sum(weight) as weight,avg(age) as age,count(id) as num from jd_user where id>5 group by sex order by sex desc limit 0,10
res = new M("user").field("sex,sum(weight) as weight,avg(age) as age,count(id) as num").where("id>?",5).group("sex").order("sex desc").page(1, 10).select(User.class);

long num= new M("user").where("id>3").count();
System.out.println("count:"+num);
num= D.M("user").fetchSql(true).where("id>3").count("id");
System.out.println("count:"+num);
num= (long) D.M("user").fetchSql(false).where("id<0").max("id");
System.out.println("max:"+num);
num= (long) D.M("user").where("id<3").max("id");
System.out.println("max:"+num);
num= (long) D.M("user").min("id");
System.out.println("min:"+num);
num= (long) D.M("user").where("id>3").min("id");
System.out.println("min:"+num);
num= (long) D.M("user").fetchSql(false).where("id>3").avg("id");
System.out.println("avg:"+num);
double avg= D.M("user").fetchSql(false).where("id>3").avg("id");
System.out.println("avg:"+avg);
num= (long) D.M("user").where("id>13441").sum("age");
System.out.println("sum:"+num);
```

user��ṹ(user table fields)��

|�ֶ���(Field Name) | ��������(Data Type) | ��ע
|--------|--------|--------
|id |int | �û�id,����������(primary key auto_increment)
|name | varchar | �û���
|age |tinyint|����
|weight | float | ����
|sex|tinyint|�Ա� 0Ů/1��(0:women/1:man)
|time|int|ʱ��

`select()`�� `find()`��ѯ�����װ��JavaBean�ﷵ�أ�
The return value of `select()` and `find()` will be saved to a JavaBean such as:
```

public class User {
	
	private long id;
	private int age;
	private String name;
	private float weight;
	private int sex;
	private int num;
	private long time;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}

```


> ͨ������`fetchSql(true)`���������Ի�ȡ�� `ThinkJD`������SQL���(Exception��ʽ)���Ҳ���ִ�����ݿ������
> by calling the method of `fetchSql(true)`,you can get the SQL statement  `ThinkJD` produced(exception way) and there will be no operation for the database.

## 0x05 ��������(insert method)
| ����(Operation)| ����(Param)| ˵��(Note) | 
| -------- | -------- | --------
|add|long add()|ǰ�᷽��:field() must be called;<br>�����Զ����ɵ�����ֵ(return the id which is a auto_increment primary key);
```
//ָ�������ֶ�insert fields specified
long id=D.M("user").field("name,weight","Tom",60).add();

/*��ָ�������ֶ�,��һ�������̶�Ϊ""��null,�ڶ���������ӦidΪnull
 *insert fields unspecified.The 1st parameter must be "" or null
 *and the 2nd parameter `null` point to `id`
 */
id=D.M("user").field("",null,"Tom",60).add();
```

## 0x06 ��������(update method)
| ����(Operation)| ����(Param)|˵��(Note)
| -------- | -------- | -------- 
|save|long save()|ǰ�᷽��:field(),where() must be called;<br>����ִ����Ч����(return the affected number of rows)

```
long num=D.M("user").field("name,weight","Mike",100).where("id=?",1234).save();
num=D.M("user").field("weight",100).where("id>?",1234).save();

```

## 0x07 ɾ������(delete method)
| ����(Operation)| ����(Param)|˵��(Note) | 
| -------- | -------- | -------- 
|delete|long delete()|ǰ�᷽��:field() must be called;;<br>����ִ����Ч����(return the affected number of rows)

`ע��Ϊ��ֹ��ɾ����where��������Ϊ�ա�`

`To avoid careless deletion, [where] conditions mustn't be null`
```
long num=D.M("user").delete(13424);
num=D.M("user").delete("time",1523681398);
num=D.M("user").where("id>=?",13421).delete();
```

## 0x08 ִ��SQL(execute method)

| ����(Operation)| ����(Param)|˵��(Note) | 
| -------- | -------- | -------- 
|execute|void execute(String... sqls)|ֱ��ִ��SQL���(execute the statements directly)

```
D.M().execute( sql1 [ sql2 , sql3 ... ] );
```

# 3 ���֤(License)

[Apache License 2.0](https://github.com/Leytton/ThinkJD/blob/master/LICENSE)

# 4 ����(About)
���ϲ���Ļ�������������֪��Ŷ~���ҵ������õø�˳�ֵ�JDBC��֮ǰ�������Ŀ��������¡�

if you like this project,star it to let me know :) .Before finding a more convenient JDBC lib,I'll update it continuously.