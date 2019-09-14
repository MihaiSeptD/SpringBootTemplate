module TemplateCRUD {
	exports template.store;

	requires slf4j.api;
	requires spring.boot;
	requires spring.context;
	requires spring.core;
	requires spring.data.neo4j;
	requires spring.boot.autoconfigure;
	requires spring.web;
	requires org.neo4j.ogm.core;
	requires spring.data.commons;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	requires spring.beans;
	requires java.annotation;
	requires tomcat.embed.core;
}