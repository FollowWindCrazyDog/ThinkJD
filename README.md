[�����ĵ�](https://blog.csdn.net/Leytton/article/details/80021702)
[English Document](https://blog.csdn.net/Leytton/article/details/80021029)

# 1 ���

`ThinkJD`������`ThinkJDBC`��һ������ǿ��Ŀ�ԴJDBC�����⡣�����ʹ��Java��`ThinkPHP`��ܵ�M����һ����`һ�д���㶨���ݿ����`��

**�ȶ�Ϊ�죺**
```
//���ݿ�����(ֻ�����һ��)
D.setDbConfig("jdbc:mysql://127.0.0.1:3306/DbName?characterEncoding=UTF-8","root","root");
//��������
long id=D.M("user").field("name,weight","Tom",60).add();
//��������
D.M("user").field("weight",100).where("id=?",id).save();
//��ѯ����
User user=D.M("user").find(User.class,id);
//ɾ������
D.M("user").delete(id);
```

**��Ŀ��ҳ** https://github.com/Leytton/ThinkJD (Github)   https://gitee.com/Leytton/ThinkJD (����)

**������Ŀ** https://github.com/Leytton/ThinkJD_Demo

**������ҳ** https://blog.csdn.net/Leytton

# 2 ʹ�÷���

## 0x01 �������
��[ThinkJDBC-x.x.x.jar](https://github.com/Leytton/ThinkJD/releases)�������������������ӵ���Ŀ����·���

 - mysql-connector-java-5.1.39.jar
 - commons-dbutils-1.6.jar

 ������Jar��������Ŀ¼�
 
 https://github.com/Leytton/ThinkJD/tree/master/lib
 
## 0x02 �������ݿ�
ThinkJD֧��ֱ�Ӷ����û�������������ݿ⣬Ҳ֧��ʹ��Hikari��C3P0�����ݿ����ӳء�

**���ȶ������ݿ����ӷ�ʽ��**

### (1)�ʺ�����������ݿ�
```
D.setDbConfig("jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=UTF-8","root","root");
```

### (2)ʹ�����ݿ����ӳ�
����ʹ��Hikari���ӳأ� 
```
HikariConfig config = new HikariConfig("/hikari.properties");
HikariDataSource dataSource = new HikariDataSource(config);
D.setDataSource(dataSource);
```
`ע��������������ݿ����ӳأ�ThinkJD������ʹ�á�`


### (3)���ñ�ǰ׺
ֻ�����һ�Σ����ñ�ǰ׺���Ǳ����
```
D.setTablePrefix("jd_");
//D.M('user') ������� `jd_user` ��
```

## 0x03 ���˷���
| ����| ����| ʾ�� |˵��| 
| ------------- |------------- |------------- | -------------
|`table`|table(String table) | table("user") | 
|`join` |join(String join)| join("left join machine on user.id=user_id and machine_status=1")|
|`field`|��field(String filed)<br>��field(String filed, Object... dataParam)| ��field("id,username")<br>��field("id,username",1111,"Leytton")| �����ڲ�ѯ����<br>�����ڸ��²���
|`where`|��where(String where)<br>��where(String where, Object... whereParam)|��where("id=1111 and username='Leytton'")<br>��where("id=? and username=?",1111,"Leytton")
|`group`|group(String group)|group("type")
|`having`|having(String having)|having("id>1234")
|`order`|order(String order)|order("id desc")
|`page`|page(long page, long rows)|page(1,10)
|`limit`|��limit(long rows)<br>��limit(long offset, long rows)|��limit(10)<br>��limit(1,10)
|`union`|union(String union,Boolean isAll)|��union("select from user_two where id>1234",false)<br>��union("select from user_two where id>1234",true)

## 0x04 ��ѯ����

| ����| ����| ˵��|
| -------- |--------|--------
|select|<`T`> List<`T`> select(Class<`T`> type)
|find|��<`T`> T find(Class<`T`> type)<br>��<`T`> T find(Class<`T`> type, long id)<br>��<`T`> T find(Class<`T`> type, String key, Object value)
|count|��long count()<br>��long count(String field)
|max|double max(String field)
|min|double min(String field)
|avg|double avg(String field)
|sum|double sum(String field)

```
//select * from user where id=3 limit 0,1
User user = D.M("user").find(User.class,3);

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

user��ṹ��

|�ֶ��� | ��������| ��ע|
|--------|--------|--------
|id |int | �û�id,����������
|name | varchar | �û���
|age |tinyint|����
|weight | float | ����
|sex|tinyint|�Ա� 0Ů/1��
|time|int|ʱ��

`select()`�� `find()`��ѯ�����װ��JavaBean�ﷵ�أ�


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

## 0x05 ��������
| ����| ����| ˵��|
| -------- | -------- | --------
|add|long add()|ǰ�᷽��:field()<br>�����Զ����ɵ�����ֵ;
```
//ָ�������ֶ�
long id=D.M("user").field("name,weight","Tom",60).add();

/*��ָ�������ֶ�,��һ�������̶�Ϊ""��null,�ڶ���������ӦidΪnull
 */
id=D.M("user").field("",null,"Tom",60).add();
```

## 0x06 ��������
| ����| ����|˵��|
| -------- | -------- | -------- 
|save|long save()|ǰ�᷽��:field(),where();<br>����ִ����Ч����

```
long num=D.M("user").field("name,weight","Mike",100).where("id=?",1234).save();
num=D.M("user").field("weight",100).where("id>?",1234).save();

```

## 0x07 ɾ������
| ����| ����|˵��|
| -------- | -------- | -------- 
|delete|long delete()|ǰ�᷽��:field()<br>����ִ����Ч����

`ע��Ϊ��ֹ��ɾ����where��������Ϊ�ա�`

```
long num=D.M("user").delete(13424);
num=D.M("user").delete("time",1523681398);
num=D.M("user").where("id>=?",13421).delete();
```

## 0x08 ִ��SQL

| ����| ����|˵��| 
| -------- | -------- | -------- 
|execute|void execute(String... sqls)|ֱ��ִ��SQL���

```
D.M().execute( sql1 [ sql2 , sql3 ... ] );
```

# 3 ���֤

[Apache License 2.0](https://github.com/Leytton/ThinkJD/blob/master/LICENSE) 

# 4 ����
���ϲ���Ļ�������������֪��Ŷ~���ҵ������õø�˳�ֵ�JDBC��֮ǰ�������Ŀ��������¡�