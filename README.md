[**�����ĵ�**](https://blog.csdn.net/Leytton/article/details/80021702)

[**English Document**](https://blog.csdn.net/Leytton/article/details/80021029)

���°汾 V1.2.3

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

**���ݿ����ӷ�ʽ�����֣�**

### (1)�����ļ���ʽ
����Ŀ��Ŀ¼������ļ�(��Hikari�����ļ���ʽһ��) 
�����һ������ʱ���Զ����ض�ȡ�����ļ�������ļ�����������ԡ���V1.2.4_5 ���ӹ��ܡ�
`thinkjdbc.properties`
```
jdbcUrl = jdbc:mysql://127.0.0.1:3306/thinkjdbc?useUnicode=true&characterEncoding=UTF-8
dataSource.user = root
dataSource.password = root
```

### (2)�ʺ����뷽ʽ
```
D.setDbConfig("jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=UTF-8","root","root");
```

### (3)ʹ�����ݿ����ӳ�
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

> ע:`D.M('user').prefix('jd_')`�����ɵ���ָ����ǰ׺��V1.2.3������

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
|`union`|union(String union,Boolean isAll)|��union("select * from user_two where id>1234",false)<br>��union("select * from user_two where id>1234",true)

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
//find��ѯ
//select id,name from jd_user where id>4 order by id asc limit 0,1
User res = D.M("user").field("id,name").where("id>?",4).order("id asc").find(User.class);

//find ����id��ѯ
//select * from user where id=3 limit 0,1
User user = D.M("user").find(User.class,3);

//find�����ֶβ�ѯ
//select * from jd_user where name='Tom' limit 0,1
User user=D.M("user").fetchSql(true).find(User.class,"name","Bob");

//where,field����
//select id,name,weight from jd_user where id>3
List<User> res = new M("user").field("id,name,weight").where("id>3").select(User.class);

//group�����ѯ
//select sex,sum(weight) as weight,avg(age) as age,count(id) as num from jd_user where id>5 group by sex order by sex desc limit 0,10
res = new M("user").field("sex,sum(weight) as weight,avg(age) as age,count(id) as num").where("id>?",5).group("sex").order("sex desc").page(1, 10).select(User.class);

//join�����ѯ
//select jd_user.id,name,weight,sum(gold) as num from jd_user left join jd_gold on user_id=jd_user.id where jd_user.id>3 group by jd_user.id
res = new M("user").field("jd_user.id,name,weight,sum(gold) as num").join("left join jd_gold on user_id=jd_user.id").where("jd_user.id>3").group("jd_user.id").select(User.class);

//union�����ѯ
//(select id,name from jd_user where id=4 ) union all (select id,name from jd_user where id<3) union (select id,name from jd_user where id=3)
res = new M("user").field("id,name").where("id=4").union("select id,name from jd_user where id<3",true)
					.union("select id,name from jd_user where id=3",false).select(User.class);

//ͳ�Ʋ�ѯ
long num= new M("user").where("id>3").count();
num= D.M("user").fetchSql(true).where("id>3").count("id");
num= (long) D.M("user").fetchSql(false).where("id<0").max("id");
num= (long) D.M("user").where("id<3").max("id");
num= (long) D.M("user").min("id");
num= (long) D.M("user").where("id>3").min("id");
num= (long) D.M("user").fetchSql(false).where("id>3").avg("id");
double avg= D.M("user").fetchSql(false).where("id>3").avg("id");
num= (long) D.M("user").where("id>13441").sum("age");
```

> ͨ������`fetchSql(true)`���������Ի�ȡ�� `ThinkJD`������SQL���(Exception��ʽ)���Ҳ���ִ�����ݿ������
![fetchSql](https://img-blog.csdn.net/2018042023324417?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0xleXR0b24=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

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