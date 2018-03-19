<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.cll.tag" prefix="pe"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>开始使用Layui</title>
  <link rel="stylesheet" href="layui/css/layui.css">
  <style type="text/css">
  	.site-title{ margin: 30px 0 20px;}
	.site-title fieldset{border: none; padding: 0; border-top: 1px solid #eee;}
	.site-title fieldset legend{margin-left: 20px;  padding: 0 10px; font-size: 22px; font-weight: 300;}
  	
  	.site-block{margin:10px;padding: 20px; border: 1px solid #eee;}
  	.site-text{position:relative;}
  	
  	.site-demo-upbar{position: absolute; top: 50%; left: 50%; margin: -18px 0 0 -91px;}
  	.layui-upload-button{background-color: rgba(0,0,0,.2); color: rgba(255,255,255,1);}
  </style>
</head>
<body>
<ul class="layui-nav">
  <li class="layui-nav-item layui-this"><a href="">最新活动</a></li>
  <li class="layui-nav-item"><a href="">产品</a></li>
  <li class="layui-nav-item"><a href="">解决方案</a></li>
  <li class="layui-nav-item"><a href="">大数据</a></li>
</ul>

<blockquote class="layui-elem-quote layui-quote-nm">
	<button class="layui-btn layui-btn-primary">原始按钮</button>
    <button class="layui-btn">默认按钮</button>
    <button class="layui-btn layui-btn-warm">暖色按钮</button>
	<a class="layui-btn layui-btn-normal" href="helloAction.do!hello?UserId=123你好！">helloAction请求</a>
	<a class="layui-btn layui-btn-danger" href="requestAction.do?BeginDate=2016-10-18">requestAction请求</a>
	
	<button class="layui-btn layui-btn-primary layui-btn-radius">原始按钮</button>
    <button class="layui-btn layui-btn-radius">默认按钮</button>
    <button class="layui-btn layui-btn-normal layui-btn-radius">百搭按钮</button>
    <button class="layui-btn layui-btn-warm layui-btn-radius">暖色按钮</button>
    <button class="layui-btn layui-btn-danger layui-btn-radius">警告按钮</button>
    <button class="layui-btn layui-btn-disabled layui-btn-radius">禁用按钮</button>
</blockquote>

<fieldset class="layui-elem-field layui-field-title">
  <legend>用户注册<fns:token/> </legend>
</fieldset> 

<div class="site-text site-block">
      <form class="layui-form layui-form-pane" action="helloAction.do!hello">
      <pe:token/>
        <div class="layui-form-item">
          <label class="layui-form-label">用户名</label>
          <div class="layui-input-inline">
            <input type="text" name="UserId" value="你好" required="" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
          </div>
          <div class="layui-form-mid layui-word-aux">由6~20位数字字母组合</div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">身份证号</label>
          <div class="layui-input-inline">
            <input type="text" name="IdNo" required="" lay-verify="required" placeholder="请输入真实身份证号" autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">个性图片</label>
          <div class="layui-input-inline">
          	<img src="GetImgAction.do" id="ImgPhoto" style="width:120px;height:120px;border: 1px solid #efefef;" class="layui-circle">
          	<div class="site-demo-upbar">
	          	<input type="file" name="file" class="layui-upload-file"> 
	  		</div>
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">性别</label>
          <div class="layui-input-block">
            <input type="radio" name="sex" value="M" title="男" checked=""><div class="layui-unselect layui-form-radio layui-form-radioed"><i class="layui-anim layui-icon"></i><span>男</span></div>
            <input type="radio" name="sex" value="F" title="女"><div class="layui-unselect layui-form-radio"><i class="layui-anim layui-icon"></i><span>女</span></div>
          </div>
        </div>
        <div class="layui-form-item">
	        <label class="layui-form-label">出生日期</label>
	        <div class="layui-input-inline">
	        	<input placeholder="请输入出生日期" onclick="layui.laydate({elem: this, festival: true,format: 'YYYY-MM-DD'})" class="layui-input">
	        </div>	
	        <div class="layui-input-inline">
	        	<input placeholder="只能选今天以后" onclick="layui.laydate({elem: this, min: laydate.now(+0)})" class="layui-input">
	        </div>	
    	</div>
        <div class="layui-form-item">
          <label class="layui-form-label">密码</label>
          <div class="layui-input-inline">
            <input type="password" name="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
          </div>
          <div class="layui-form-mid layui-word-aux">由6~20位数字字母组合</div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">工作地址</label>
          <div class="layui-input-block">
            <select name="city">
              <option value=""></option>
              <option value="0">北京</option>
              <option value="1">上海</option>
              <option value="2">广州</option>
              <option value="3">深圳</option>
              <option value="4">杭州</option>
            </select>
				<div class="layui-unselect layui-form-select">
					<div class="layui-select-title">
						<input type="text" placeholder="请选择" value="" readonly="" class="layui-input layui-unselect"> 
							<i class="layui-edge"></i>
					</div>
					<ul class="layui-anim layui-anim-upbit">
						<li lay-value="0">北京</li>
						<li lay-value="1">上海</li>
						<li lay-value="2">广州</li>
						<li lay-value="3">深圳</li>
						<li lay-value="4">杭州</li>
					</ul>
				</div>
			</div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">爱好</label>
          <div class="layui-input-block">
            <input type="checkbox" name="like[write]" title="写作" checked=""><div class="layui-unselect layui-form-checkbox  layui-form-checked"><span>写作</span><i class="layui-icon"></i></div>
            <input type="checkbox" name="like[read]" title="阅读"><div class="layui-unselect layui-form-checkbox"><span>阅读</span><i class="layui-icon"></i></div>
            <input type="checkbox" name="like[dai]" title="发呆"><div class="layui-unselect layui-form-checkbox"><span>发呆</span><i class="layui-icon"></i></div>
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">开关</label>
          <div class="layui-input-block">
            <input type="checkbox" name="switch" lay-skin="switch"><div class="layui-unselect layui-form-switch"><i></i></div>
          </div>
        </div>
        <div class="layui-form-item">
		    <label class="layui-form-label">价格范围</label>
		    <div class="layui-input-inline" style="width: 100px;">
		      <input type="text" name="price_min" placeholder="￥" autocomplete="off" class="layui-input">
		    </div>
		    <div class="layui-form-mid">-</div>
		    <div class="layui-input-inline" style="width: 100px;">
		      <input type="text" name="price_max" placeholder="￥" autocomplete="off" class="layui-input">
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label">时间范围</label>
		    <div class="layui-input-inline">
		      <input class="layui-input" placeholder="开始日" id="BeginDate">
		    </div>
		    <div class="layui-input-inline">
		      <input class="layui-input" placeholder="截止日" id="EndDate">
		    </div>
		 </div>
        <div class="layui-form-item layui-form-text">
          <label class="layui-form-label">文本域</label>
          <div class="layui-input-block">
            <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
          </div>
        </div>
        <div class="layui-form-item">
          <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
          </div>
        </div>
      </form>
    </div>

<fieldset class="layui-elem-field layui-field-title">
  <legend>Layer的使用</legend>
</fieldset> 
	 
	<button class="layui-btn" onclick="showLayer(1)">弹出提示消息层</button>
    <button class="layui-btn" onclick="showLayer(2)">弹出询问层</button>
    <button class="layui-btn" onclick="showLayer(3)" id="tip-bth">弹出按钮提示</button>
    <button class="layui-btn" onclick="showLayer(4)">弹出页面层</button><br/><br/>
    <button class="layui-btn" onclick="showLayer(5)">弹出Prompt层</button>
    <button class="layui-btn" onclick="showLayer(6)">弹出等待加载层</button>
    <button class="layui-btn" onclick="showLayer(7)">弹出iframe层</button>
    <button class="layui-btn" onclick="showLayer(8)">弹出Toast提示</button> 

<fieldset class="layui-elem-field layui-field-title">
  <legend>分页的使用</legend>
</fieldset>
<div id="PageDemo"></div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>模板TPL的使用</legend>
</fieldset>
<blockquote class="layui-elem-quote layui-quote-nm">
<div id="TplDemo"></div>
</blockquote>
<script id="TplId" type="text/html">
<h3>{{ d.title }}</h3>
<ul>
{{#  layui.each(d.list, function(index, item){ }}
  <li>
    <span>{{ item.modname }}</span>
    <span>{{ item.alias }}：</span>
    <span>{{ item.site || '' }}</span>
  </li>
{{#  }); }}
 
{{#  if(d.list.length === 0){ }}
  无数据
{{#  } }} 
</ul>
</div>
</script>
<fieldset class="layui-elem-field layui-field-title">
  <legend>编辑器的使用</legend>
</fieldset>	
<textarea class="layui-textarea" id="LAY_demo1" style="display: none">  
  	请输入...(区别在获取html标签)
</textarea>
<div class="site-demo-button" style="margin-top: 20px;">
  <button class="layui-btn site-demo-layedit" data-type="content">获取编辑器内容</button>
  <button class="layui-btn site-demo-layedit" data-type="text">获取编辑器纯文本内容</button>
</div>
	 
<script src="layui/layui.js"></script>
<script src="assets/js/jquery.js"></script>
<script src="assets/js/common.js"></script>
<script>
//一般直接写在一个js文件中
var layer;
var $ ;
layui.use(['layer', 'form','element','laydate','laypage','laytpl','layedit','upload','util','jquery'], function(){
  layer = layui.layer;
  var form = layui.form();
  var element = layui.element();//导航的hover效果、二级菜单等功能
  var laydate = layui.laydate;
  var laypage = layui.laypage;
  var laytpl = layui.laytpl;
  var layedit = layui.layedit;
  $ = layui.jquery;
  var upload = layui.upload;
  var util = layui.util;
  
  //layer组件
  //layer.msg('开启LayUI之旅吧！');
  
  //时间控件
  var start = {
    min: laydate.now()
    ,max: '2099-06-16 23:59:59'
    ,istoday: false
    ,choose: function(datas){
      end.min = datas; //开始日选好后，重置结束日的最小日期
      end.start = datas //将结束日的初始值设定为开始日
    }
  };
  
  var end = {
    min: laydate.now()
    ,max: '2099-06-16 23:59:59'
    ,istoday: false
    ,choose: function(datas){
      start.max = datas; //结束日选好后，重置开始日的最大日期
    }
  };
  
  document.getElementById('BeginDate').onclick = function(){
    start.elem = this;
    laydate(start);
  }
  document.getElementById('EndDate').onclick = function(){
    end.elem = this
    laydate(end);
  }
  
  //分页控件
  laypage({
    cont: 'PageDemo',
    pages: 100,
    skip: true,
    skin: '#1E9FFF'
  });
  
  //模板使用
  var jsonData ={
  "title": "Layui常用模块"
  ,"list": [
	    {
	      "modname": "弹层"
	      ,"alias": "layer"
	      ,"site": "layer.layui.com"
	    }
	    ,{
	      "modname": "表单"
	      ,"alias": "form"
	    }
	    ,{
	      "modname": "分页"
	      ,"alias": "laypage"
	    }
	    ,{
	      "modname": "日期"
	      ,"alias": "laydate"
	    }
	    ,{
	      "modname": "上传"
	      ,"alias": "upload"
	    }
	  ]
	};
    var getTpl = document.getElementById('TplId').innerHTML;
    var TplDemo = document.getElementById('TplDemo');
    laytpl(getTpl).render(jsonData, function(html){
    	TplDemo.innerHTML = html;
   	});
    
    //富文本编辑器
    //构建一个默认的编辑器
    var index = layedit.build('LAY_demo1');
    //编辑器外部操作
    var active = {
      content: function(){
        layer.alert(layedit.getContent(index)); //获取编辑器内容
      }
      ,text: function(){
    	layer.alert(layedit.getText(index)); //获取编辑器纯文本内容
      }
    };
    
    $('.site-demo-layedit').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    
    //上传图片
    upload({
       url: 'UploadAction.do!upload' //上传接口
       ,success: function(res){ //上传成功后的回调
    	 $('#ImgPhoto').attr('src','GetImgAction.do?fileName='+res.fileName);   
         console.log(res);
         layer.msg(res.success);
       }
     });
    
  //使用内部工具组件
    util.fixbar();
  
  //Ajax方式form提交
  $(".layui-form").on("submit",function(ev){
	   //才用自定义Ajax提交
	   var data = $('.layui-form').serialize();
	   console.log($('.layui-form').serialize());
	   post2SRV(this.action,data);
	   //阻止form的默认事件
	   ev.preventDefault();
	});
  
//改写a标签事件
  $(function() {
      $("a").each(function(){
          $(this).click(function(){
        	  var urlParam =  $(this).attr("href");//helloAction.do!hello?UserId=123你好！
        	  var Params = urlParam.split("?"); 
        	  var url = Params[0]; 
        	  var params = Params[1];
        	  post2SRV(url, params);
        	  
        	  //禁用a标签的默认行为
        	  //$(this).attr('href', '#');//这样状态栏不会显示链接地址
        	  $(this).click(function (event) {
                  event.preventDefault();   // 如果<a>定义了 target="_blank“ 需要这句来阻止打开新页面
              });
        	  return false;
              //alert($(this).text()); 
          });  
      });  
  });  
  
});

</script>

<script type="text/javascript">
		function showLayer(type){
			switch (type) {
			case 1:
				layer.alert('弹出消息层');
				break;
			case 2:
				//询问框
				layer.confirm('您是如何看待前端开发？', {
				  btn: ['重要','奇葩'] //按钮
				}, function(){
				  layer.msg('的确很重要', {icon: 1});
				}, function(){
				  layer.msg('也可以这样', {
				    time: 20000, //20s后自动关闭
				    btn: ['明白了', '知道了']
				  });
				});
				break;
			case 3:
				//tips层-右
				layer.tips('默认就是向右的', '#tip-bth');
				break;	
			case 4:
				//页面层
				layer.open({
				  type: 1,
				  skin: 'layui-layer-rim', //加上边框
				  area: ['420px', '240px'], //宽高
				  content: 'html内容'
				});
				break;	
			case 5:
				//prompt层
				layer.prompt({
				  title: '输入任何口令，并确认',
				  formType: 1 //prompt风格，支持0-2
				}, function(pass){
				  layer.prompt({title: '随便写点啥，并确认', formType: 2}, function(text){
				    layer.msg('演示完毕！您的口令：'+ pass +' 您最后写下了：'+ text);
				  });
				});
				break;	
			case 6:
				//加载层
				var index = layer.load(0, {shade: [0.2,'#C3C3C3'],time: 3*1000}); //0代表加载的风格，支持0-2
				break;	
			case 7:
				//iframe层
				layer.open({
				  type: 2,
				  title: 'layer mobile页',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['80%', '90%'],
				  content: 'http://layer.layui.com/mobile/' //iframe的url
				});
				break;	
			case 8:
				layer.msg('Hello Layui',{
				  //icon: 0,
				  time: 2000 //2秒关闭（如果不配置，默认是3秒）
				},function(){
					//do something
				});
				break;	
			default:
				break;
			}
		}
	</script>
	<script type="text/javascript">
	
	</script>
</body>
</html>