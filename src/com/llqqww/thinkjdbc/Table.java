package com.llqqww.thinkjdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//��ע����������TYPEԪ�أ�Ҳ����������ͽӿڵ�����
@Retention(RetentionPolicy.RUNTIME)//��ע����Ϣ����ʱ����
@Documented//��ע�������Javadoc��
public @interface Table {
	public String name() default "";
}
