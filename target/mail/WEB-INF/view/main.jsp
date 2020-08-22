<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>yukino</title>
	    <link href="css/bootstrap.min.css" rel="stylesheet">
	    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
	    <link href="css/plugins/iCheck/custom.css" rel="stylesheet">
	    <link href="css/plugins/summernote/summernote-bs4.css" rel="stylesheet">
	    <link href="css/animate.css" rel="stylesheet">
	    <link href="css/style.css" rel="stylesheet">
		<!-- Mainly scripts -->
		<script src="js/jquery-3.1.1.min.js"></script>
		<script src="js/popper.min.js"></script>
		<script src="js/bootstrap.js"></script>
		<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
		<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="./js/bootstrap-dialog.js"></script>
		<script src="./js/ajaxfileupload.js"></script>
		<!-- Custom and plugin javascript -->
		<script src="js/inspinia.js"></script>
		<script src="js/plugins/pace/pace.min.js"></script>
		<!-- iCheck -->
		<script src="js/plugins/iCheck/icheck.min.js"></script>
		<!-- SUMMERNOTE -->
		<script src="js/plugins/summernote/summernote-bs4.js"></script>
		<script src="css/plugins/summernote/lang/summernote-zh-CN.js"></script>
		<script>
		    $(function(){
		        $('.i-checks').iCheck({
		            checkboxClass: 'icheckbox_square-green',
		            radioClass: 'iradio_square-green',
		        });
		        $('.summernote').summernote({
					height: '150px',
		        	lang:'zh-CN',
		        	toolbar: [
				    ['style', ['style']],
				    ['font', ['bold', 'italic', 'underline', 'clear']],
				    ['fontname', ['fontname']],
				    ['color', ['color']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']],
				    ['table', ['table']],
				    ['help', ['help']]
				]});
		    });
			function change(id) {
				$("#compose").css("display","none");//写邮件
				$("#inbox").css("display","none");//收件箱
				$("#outbox").css("display","none");//发件箱
				$("#draftbox").css("display","none");//草稿箱
				$("#bin").css("display","none");//垃圾箱
				id.css("display","block");//写邮件
			}
			function MessageSend() {
				var toUser = $("[name='toUser']").val();
				var subject = $("[name='subject']").val();
				var content = $('.summernote').summernote("code");
				content = encodeURIComponent(content);
				var obj = {
					url:"./MessageSendServlet",
					type:"post",
					data:"toUser="+toUser+"&subject="+subject+"&content="+content,
					success:function (data){
						if (data=="1"){
							alert("发送成功");
							return;
						}
						else {
							alert("发送失败,请稍后重试");
						}
					}
				};
				$.ajax(obj);
			}
			//退出登录
			function Login() {
				location.href="./IndexServlet";
			}
			//修改登录密码
			function passwordUpdate() {
				Bootstrap.dialog({
					title: '修改密码',
					url: './passwordFrag.jsp',
					onOK: function() {
						var oldPassword = $("[name='old']").val();
						var newPassword = $("[name='new']").val();
						var rePassword = $("[name='re_new']").val();
						if (oldPassword==""||oldPassword==undefined){
							Bootstrap.tip("请输入旧密码");
							return;
						}
						if (newPassword==""||newPassword==undefined){
							Bootstrap.tip("请输入新密码");
							return;
						}if (newPassword!=rePassword){
							Bootstrap.tip("两次密码不一致，请重试");
							return;
						}
						var obj={
							url:"./PasswordServlet?password="+oldPassword,
							success:function (data) {
								if (data=="1"){
									obj={
										url:"./PasswordServlet",
										type: "post",
										data:"password="+newPassword,
										success:function (data) {
											alert(data);
											if (data=="1"){
												Bootstrap.tip("操作成功");
												setTimeout(Login,3000);
											}else {
												Bootstrap.tip("修改失败");
											}
										}
									}
									$.ajax(obj);
								}else {
									Bootstrap.tip("旧密码错误，请重试");
								}
							}
						}
						$.ajax(obj);
					}
				});
			}
			//修改用户头像
			function imgChange() {
				var dialog = Bootstrap.dialog({
					title: '修改图片',
					url: './UserInfoImageServlet',
					onOK: function() {
						console.log(new FormData(document.getElementById("image_update")));
						var object = {
							url:"./UserInfoImageServlet",
							type:"post",
							data:new FormData(document.getElementById("image_update")),//1、将form表单元素的name与value进行组合，实现表单数据的序列化，以减少表单元素的拼接，提高工作效率；2、异步上传文件
							dataType : "text",
							cache: false,
							processData: false, // 不处理数据
							contentType: false,// 不设置内容类型
							success:function(data){
								if (data=="1"){
									Bootstrap.tip("添加成功");
									dialog.modal('hide');
									return;
								}
								if (data=="2"){
									Bootstrap.tip("文件太大，添加失败");
									dialog.modal('hide');//隐藏模态框
									return;
								}else{
									Bootstrap.tip("添加失败");
									dialog.modal('hide');//隐藏模态框
									return;
								}
							}
						};
						$.ajax(object);
					},
				});
			}
		</script>
		<style>
			#c_image{
				cursor: pointer;
			}
		</style>
		<script>
			<%--收件箱--%>
			var inboxPageNO = 1;
			var inboxPageTotal;
			function inboxPageUp() {
				if (inboxPageNO==1){
					alert("已经是第一页");
					return;
				}
				inboxPageNO = inboxPageNO -1;
				inboxPage();
			}
			function inboxPageDown() {
				if (inboxPageNO==inboxPageTotal){
					alert("已经是最后一页");
					return;
				}
				inboxPageNO++;
				inboxPage();
			}
			function inboxPage() {
				var obj = {
					url: "./MessageInboxServlet?pageNo="+inboxPageNO+"&from="+$("[name='inbox_from']").val(),
					success:function (data) {
						console.log(data);
						$("#inbox_list").html(data);
						$("#inbox_span").html($("[name='inboxCount']").val())
						$("#inbox_count").html("收件箱 ("+$("[name='inboxCount']").val()+")");
						inboxPageTotal = $("[name='inboxPageTotal']").val();
					}
				};
				$.ajax(obj);
			}
			function inboxSearch() {
				inboxPageNO=1;
				inboxPage();
			}
		</script>
		<script>
		<%--发件箱--%>
		var outboxPageNO = 1;
		var outboxPageTotal;
		function outboxPageUp() {
			if (outboxPageNO==1){
				alert("已经是第一页");
				return;
			}
			outboxPageNO = outboxPageNO -1;
			outboxPage();
		}
		function outboxPageDown() {
			if (outboxPageNO==outboxPageTotal){
				alert("已经是最后一页");
				return;
			}
			outboxPageNO++;
			outboxPage();
		}
		function outboxPage() {
			var obj = {
				url: "./RecordOutboxServlet?pageNo="+outboxPageNO+"&to="+$("[name='outbox_to']").val(),
				success:function (data) {
					$("#outbox_list").html(data);
					$("#outbox_count").html(" 发件箱 ("+$("[name='outboxCount']").val()+")");
					inboxPageTotal = $("[name='outboxPageTotal']").val();
				}
			};
			$.ajax(obj);
		}
		function outboxSearch() {
			outboxPageNO=1;
			outboxPage();
		}
		</script>
        <script>
         <%--草稿箱--%>
         var draftboxPageNO=1;
         var draftboxPageTotal;
         function draftboxPageUp() {
             if (draftboxPageNO==1){
                 alert("已经是第一页");
                 return;
             }
             draftboxPageNO = draftboxPageNO -1;
             draftboxPage();
         }
         function draftboxPageDown() {
             if (draftboxPageNO==draftboxPageTotal){
                 alert("已经是最后一页");
                 return;
             }
             draftboxPageNO++;
             draftboxPage();
         }
         function draftboxPage() {
             var obj = {
                 url: "./DraftInboxServlet?pageNo="+draftboxPageNO+"&to="+$("[name='draftbox_to']").val(),
                 success:function (data) {
                     $("#draftbox_list").html(data);
                     $("#draftbox_span").html($("[name='draftboxCount']").val())
                     $("#draftbox_count").html("草稿箱 ("+$("input[name='draftboxCount']").val()+")");
                     draftboxPageTotal = $("input[name='draftboxPageTotal']").val();
                 }
             };
             $.ajax(obj);
         }
         function draftboxSearch() {
             draftboxPageNO=1;
             inboxPage();
         }
         //存到草稿箱
         function draftboxSave() {
			 alert("存入草稿箱")
			 var to = $("[name='toUser']").val();
			 var subject = $("[name='subject']").val();
			 var content = $('.summernote').summernote("code");
			 content = encodeURIComponent(content);
			 var obj = {
				 url:"./DraftInboxSaveServlet",
				 type:"post",
				 data:"to="+to+"&subject="+subject+"&content="+content,
				 success: function (data) {
					 if (data=="1"){
					 	alert("保存成功");
					 	change($('#compose'));
					 }else {
					 	alert("保存失败");
					 }
				 }
			 }
			 $.ajax(obj);
		 }
		 <%--删除草稿箱方法--%>
			function draftboxDelect() {
				var s='';
				$('input[name="draftboxCheckbox"]:checked').each(function(){
					s+=$(this).val()+','; //遍历得到所有checkbox的value
				});
				if (s.length > 0) {
					//删除多出来的“，”
					s = s.substring(0,s.length - 1);
				}
				console.log(s);
				//异步删除操作
				var obj = {
					url:"./DraftInboxDeleteServlet?id="+s+"",
					success: function (data) {
						if (data=="1"){
							alert("删除成功");
							draftboxPage();
						}
						else alert("删除失败");
					}
				}
				$.ajax(obj);
			}
		</script>
	</head>
	<body>
	    <div id="wrapper">
	        <div id="page-wrapper" class="gray-bg">
	        	<div class="nav-header">
                    <div class="dropdown profile-element">
						<c:if test="${empty userInfo.imageName}">
							<img id="c_image" onclick="imgChange()"  alt="图片" class="rounded-circle" src="./img/default.jpg" width="60px">
						</c:if>
						<c:if test="${not empty userInfo.imageName}">
							<img id="c_image" onclick="imgChange()" alt="图片" class="rounded-circle" src="${file_server}${userInfo.imageName}">
						</c:if>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        	<!--账户名-->
                            <span id="account_name" class="block m-t-xs font-bold">${userInfo.userName}</span>
                            <span class="text-muted text-xs block">设置<b class="caret"></b></span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="dropdown-item" onclick="userInfoUpdate()" href="###">修改信息</a></li>
                            <li><a class="dropdown-item" onclick="passwordUpdate()" href="###">修改密码</a></li>
                            <li class="dropdown-divider"></li>
                            <li><a class="dropdown-item" onclick="Login()">退出登录</a></li>
                        </ul>
                    </div>
                </div>
	        	<div class="wrapper wrapper-content">
			        <div class="row">
			            <div class="col-lg-3">
			                <div class="ibox ">
			                    <div class="ibox-content mailbox-content">
			                    	<!--操作列表-->
			                        <div class="file-manager">
			                            <a class="btn btn-block btn-primary compose-mail" href="###" onclick="change($('#compose'))">写邮件</a>
			                            <div class="space-25"></div>
			                            <h5>文件夹</h5>
			                            <ul class="folder-list m-b-md" style="padding: 0">
			                                <li><a id="get" href="###" onclick="change($('#inbox'));inboxPage()"> <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning float-right" id="inbox_span"></span> </a></li>
			                                <li><a href="###" onclick="change($('#outbox'));outboxPage()"> <i class="fa fa-envelope-o"></i> 发件箱 </a></li>
			                                <li><a href="###" onclick="change($('#draftbox'));draftboxPage()"> <i class="fa fa-file-text-o"></i> 草稿箱 <span class="label label-danger float-right" id="draftbox_span"></span></a></li>
			                                <li><a href="###" onclick="change($('#bin'))"> <i class="fa fa-trash-o"></i> 垃圾箱</a></li>
			                            </ul>
			                            <div class="clearfix"></div>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div id="compose" class="col-lg-9 animated fadeInRight">
			            <div class="mail-box-header">
			                <h2>
                				写邮件
			                </h2>
			            </div>
        				<div class="mail-box">
            				<div class="mail-body">
		                    	<form>
			                        <div class="form-group row">
			                        	<label class="col-sm-2 col-form-label">收件人：</label>
			                            <div class="col-sm-10">
			                            	<input type="text" class="form-control" name="toUser">
			                            </div>
			                        </div>
			                        <div class="form-group row"><label class="col-sm-2 col-form-label">主题：</label>
			                            <div class="col-sm-10"><input type="text" class="form-control" name="subject"></div>
			                        </div>
		                        </form>
            				</div>
		                    <div class="mail-text h-200" style="margin-top: 10px">
		                        <div class="summernote" style="display: none;">

								</div>
								<div class="clearfix"></div>
                    		</div>
		                    <div class="mail-body text-right tooltip-demo">
		                        <a href="###" onclick="MessageSend()" class="btn btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="发送邮件"><i class="fa fa-reply"></i> 发送</a>
		                        <a href="###" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="丢弃邮件"><i class="fa fa-times"></i> 丢弃</a>
		                        <a href="###" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至草稿箱" onclick="draftboxSave()"><i class="fa fa-pencil"></i> 草稿</a>
		                    </div>
                    		<div class="clearfix"></div>
                		</div>
		            </div>
						<div id="inbox" style="display: none;" class="col-lg-9 animated fadeInRight">
				            <div class="mail-box-header">
				            	<!--按“主题”搜索邮件-->
				                <form class="float-right mail-search">
				                    <div class="input-group">
				                        <input type="text" class="form-control form-control-sm" name="inbox_from" placeholder="按“发件人”搜索邮件">
				                        <div class="input-group-btn">
				                            <button type="button" class="btn btn-sm btn-primary" onclick="inboxSearch()"> 搜索</button>
				                        </div>
				                    </div>
				                </form>
				                <h2 id="inbox_count">
				                </h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                	<!--翻页-->
				                    <div class="btn-group float-right">
				                        <button class="btn btn-white btn-sm" onclick="inboxPageUp()"><i class="fa fa-arrow-left"></i></button>
				                        <button class="btn btn-white btn-sm" onclick="inboxPageDown()"><i class="fa fa-arrow-right"></i></button>
				                    </div>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新 收件箱"><i class="fa fa-refresh"></i> 刷新</button>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标记为已读"><i class="fa fa-eye"></i> </button>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至垃圾箱"><i class="fa fa-trash-o"></i> </button>
				                </div>
				            </div>
			                <div class="mail-box" id="inbox_list">

			                </div>
			            </div>
			        	<div id="outbox" style="display: none;" class="col-lg-9 animated fadeInRight">
				            <div class="mail-box-header">
				            	<!--按“主题”搜索邮件-->
				                <form class="float-right mail-search">
				                    <div class="input-group">
				                        <input type="text" class="form-control form-control-sm" name="outbox_to" placeholder="按“收件人”搜索邮件">
				                        <div class="input-group-btn">
				                            <button type="button" class="btn btn-sm btn-primary" onclick="outboxSearch()"> 搜索</button>
				                        </div>
				                    </div>
				                </form>
				                <h2 id="outbox_count">

				                </h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                	<!--翻页-->
				                    <div class="btn-group float-right">
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-left" onclick="outboxPageUp()"></i></button>
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-right" onclick="outboxPageDown()"></i></button>
				                    </div>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新 收件箱"><i class="fa fa-refresh"></i> 刷新</button>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至垃圾箱"><i class="fa fa-trash-o"></i> </button>
				                </div>
				            </div>
			                <div class="mail-box" id="outbox_list">

			                </div>
			            </div>
			        	<div id="draftbox" style="display: none;" class="col-lg-9 animated fadeInRight">
				            <div class="mail-box-header">
				            	<!--按“主题”搜索邮件-->
				                <form method="get" action="index.html" class="float-right mail-search">
				                    <div class="input-group">
				                        <input type="text" class="form-control form-control-sm" name="draftbox_to" placeholder="按“收件人”搜索邮件">
				                        <div class="input-group-btn">
				                            <button type="button" class="btn btn-sm btn-primary" onclick="draftboxSearch()"> 搜索</button>
				                        </div>
				                    </div>
				                </form>
				                <h2 id="draftbox_count">

				                </h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                	<!--翻页-->
				                    <div class="btn-group float-right">
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-left" onclick="draftboxPageUp()"></i></button>
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-right" onclick="draftboxPageDown()"></i></button>
				                    </div>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="删除" onclick="draftboxDelect()"><i class="fa fa-trash-o"></i> </button>
				                </div>
				            </div>
			                <div class="mail-box" id="draftbox_list">
				                <table class="table table-hover table-mail">
					                <tbody>

									</tbody>
				                </table>
			                </div>
			            </div>
			        	<div id="bin" style="display: none;" class="col-lg-9 animated fadeInRight">
				            <div class="mail-box-header">
				            	<!--按“主题”搜索邮件-->
				                <form method="get" action="index.html" class="float-right mail-search">
				                    <div class="input-group">
				                        <input type="text" class="form-control form-control-sm" name="search" placeholder="按“主题”搜索邮件">
				                        <div class="input-group-btn">
				                            <button type="submit" class="btn btn-sm btn-primary"> 搜索</button>
				                        </div>
				                    </div>
				                </form>
				                <h2>
				                   	 垃圾箱 (16)
				                </h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                	<!--翻页-->
				                    <div class="btn-group float-right">
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-left"></i></button>
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-right"></i></button>
				                    </div>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="删除"><i class="fa fa-trash-o"></i> </button>
				                </div>
				            </div>
			                <div class="mail-box">
				                <table class="table table-hover table-mail">
					                <tbody>

									</tbody>
				                </table>
			                </div>
			            </div>
						<div id="detail" style="display: none;" class="col-lg-9 animated fadeInRight">
				            <div class="mail-box-header">
				                <div class="float-right tooltip-demo">
				                    <a class="btn btn-sm btn-white" href="mail_compose.html"><i class="fa fa-reply"></i>回复</a>
		                            <a href="mailbox.html" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至垃圾箱"><i class="fa fa-trash-o"></i></a>
				                </div>
				                <h2>查看邮件</h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                    <h3>
				                        <span class="font-normal">主题：</span>
				                         Aldus PageMaker包括Lorem Ipsum的版本。</h3>
				                    <h5>
				                        <span class="float-right font-normal">2月2日上午10:15  2014年 </span>
				                        <span class="font-normal">来自：</span>
				                        alex.smith@corporation.com
				                    </h5>
				                </div>
				            </div>
				            <div class="mail-box">
	                			<div class="mail-body">
	                    			邮件内容
	                			</div>
			                        <div class="mail-body text-right tooltip-demo">
		                        </div>
	                        	<div class="clearfix"></div>
	                		</div>
            			</div>
			        </div>
		        </div>
		        <div class="footer">
					<span>10GB<span></span> - <strong>1024MB</strong> 免费版.</span>
		            <div>
		                <strong>yukino邮箱</strong>
		            </div>
		        </div>
	        </div>
	    </div>
	</body>
</html>
