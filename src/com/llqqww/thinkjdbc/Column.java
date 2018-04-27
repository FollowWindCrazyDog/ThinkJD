package com.llqqww.thinkjdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//��ע����������TYPEԪ�أ�Ҳ����������ͽӿڵ�����
@Retention(RetentionPolicy.RUNTIME)//��ע����Ϣ����ʱ����
@Documented//��ע�������Javadoc��
public @interface Column {
	String name() default "";
	boolean isKey() default false;
	boolean isAutoInc() default true;//pk�Ƿ�������
	boolean isColumn() default true;
	boolean isCheckField() default true;//�Ƿ��ϸ�����������
}