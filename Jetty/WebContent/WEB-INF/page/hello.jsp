<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>开始使用Layui</title>
  <link rel="stylesheet" href="<%=path %>/layui/css/layui.css">
</head>
<body>

<span class="layui-breadcrumb">
  <a href="/">首页</a>
  <a href="/demo/">演示</a>
  <a><cite>导航元素</cite></a>
</span>

<fieldset class="layui-elem-field layui-field-title">
  <legend>引用区块 - 一般风格</legend>
</fieldset> 

<blockquote class="layui-elem-quote">
	 helloAction.do访问成功！！<br>
	 	${text }   ${UserId }  ${submit }
</blockquote>
<blockquote class="layui-elem-quote layui-quote-nm">
  猿强，则国强。国强，则猿更强！ 
  <br>——孟子（好囖。。其实这特喵的是我说的）
</blockquote>

<fieldset class="layui-elem-field layui-field-title">
  <legend>纯圆角</legend>
 <img src="<%=path %>/assets/images/staff1.png" class="layui-circle">
</fieldset>

<div class="layui-inline">
<ul class="layui-nav layui-nav-tree">
  <li class="layui-nav-item"><a href="">最新活动</a></li>
  <li class="layui-nav-item layui-this"><a href="">产品</a></li>
  <li class="layui-nav-item"><a href="">解决方案</a></li>
  <li class="layui-nav-item"><a href="">大数据</a></li>
</ul>
</div>

<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
    <li class="layui-this">网站设置</li>
    <li>用户管理</li>
    <li>权限分配</li>
    <li>商品管理</li>
    <li>订单管理</li>
  </ul>
  <div class="layui-tab-content" style="height: 150px;">
    <div class="layui-tab-item layui-show">内容1</div>
    <div class="layui-tab-item">内容2</div>
    <div class="layui-tab-item">内容3</div>
    <div class="layui-tab-item">内容4</div>
    <div class="layui-tab-item">内容5</div>
  </div>
</div>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>基本树</legend>
</fieldset>
 
<div style="display: inline-block; width: 180px; height: 210px; padding: 10px; border: 1px solid #ddd; overflow: auto;">
  <ul id="treeDemo"></ul>
</div>
	 	
<script src="<%=path %>/layui/layui.js"></script>
<script>

layui.use(['element','tree', 'layer'], function(){
  var element = layui.element(),//Tab的切换功能，切换事件监听等，需要依赖element模块
  layer = layui.layer,
  $ = layui.jquery,
  tree = layui.tree ;
  
  tree({
	    elem: '#treeDemo' //指定元素
	    ,target: '_blank' //是否新选项卡打开（比如节点返回href才有效）
	    ,click: function(item){ //点击节点回调
	      //layer.msg('当前节名称：'+ item.name + '<br>全部参数：'+ JSON.stringify(item));
	      console.log(item);
	    }
	    ,nodes: [ //节点
	      {
	        name: '常用文件夹'
	        ,id: 1
	        ,alias: 'changyong'
	        ,children: [
	          {
	            name: '所有未读（设置跳转）'
	            ,id: 11
	            ,href: 'http://www.layui.com/'
	            ,alias: 'weidu'
	          }, {
	            name: '置顶邮件'
	            ,id: 12
	          }, {
	            name: '标签邮件'
	            ,id: 13
	          }
	        ]
	      }, {
	        name: '我的邮箱'
	        ,id: 2
	        ,spread: true
	        ,children: [
	          {
	            name: 'QQ邮箱'
	            ,id: 21
	            ,spread: true
	            ,children: [
	              {
	                name: '收件箱'
	                ,id: 211
	                ,children: [
	                  {
	                    name: '所有未读'
	                    ,id: 2111
	                  }, {
	                    name: '置顶邮件'
	                    ,id: 2112
	                  }, {
	                    name: '标签邮件'
	                    ,id: 2113
	                  }
	                ]
	              }, {
	                name: '已发出的邮件'
	                ,id: 212
	              }, {
	                name: '垃圾邮件'
	                ,id: 213
	              }
	            ]
	          }, {
	            name: '阿里云邮'
	            ,id: 22
	            ,children: [
	              {
	                name: '收件箱'
	                ,id: 221
	              }, {
	                name: '已发出的邮件'
	                ,id: 222
	              }, {
	                name: '垃圾邮件'
	                ,id: 223
	              }
	            ]
	          }
	        ]
	      }
	      ,{
	        name: '收藏夹'
	        ,id: 3
	        ,alias: 'changyong'
	        ,children: [
	          {
	            name: '爱情动作片'
	            ,id: 31
	            ,alias: 'love'
	          }, {
	            name: '技术栈'
	            ,id: 12
	            ,children: [
	              {
	                name: '前端'
	                ,id: 121
	              }
	              ,{
	                name: '全端'
	                ,id: 122
	              }
	            ]
	          }
	        ]
	      }
	    ]
	  });
  
});
</script>	 	
</body>
</html>