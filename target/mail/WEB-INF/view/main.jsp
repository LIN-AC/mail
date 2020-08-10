<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>INSPINIA | Mailbox</title>
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
			function sendPage() {
				$("#compose").css("display","block");//写邮件
				$("#inbox").css("display","none");//收件箱
				$("#outbox").css("display","none");//发件箱
				$("#draftbox").css("display","none");//草稿箱
				$("#bin").css("display","none");//垃圾箱
			}
			function MessageSend() {
				var toUser = $("[name='toUser']").val();
				var subject = $("[name='subject']").val();
				var content = $('.summernote').summernote("code");
				content = encodeURIComponent(content);
				var obj = {
					url:"./MessageSendServlet",
					type:"post",
					data:"toUSer="+toUser+"&subject="+subject+"&content="+content,
					success:function (data){
						if (data=="1"){
							Bootstrap.tip("注册成功");
						}
					}
				};
				$.ajax(obj);
			}
		</script>

	</head>
	<body>
	    <div id="wrapper">
	        <div id="page-wrapper" class="gray-bg">
	        	<div class="nav-header">
                    <div class="dropdown profile-element">
                        <img alt="图片" class="rounded-circle" src="./img/default.jpg" width="150px">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        	<!--账户名-->
                            <span id="account_name" class="block m-t-xs font-bold">李明</span>
                            <span class="text-muted text-xs block">设置<b class="caret"></b></span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="dropdown-item" href="contacts.html">修改信息</a></li>
                            <li><a class="dropdown-item" href="mailbox.html">修改密码</a></li>
                            <li class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="login.html">退出登录</a></li>
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
			                            <a class="btn btn-block btn-primary compose-mail" href="###" onclick="sendPage()">写邮件</a>
			                            <div class="space-25"></div>
			                            <h5>文件夹</h5>
			                            <ul class="folder-list m-b-md" style="padding: 0">
			                                <li><a href="mailbox.html"> <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning float-right">16</span> </a></li>
			                                <li><a href="mailbox.html"> <i class="fa fa-envelope-o"></i> 发件箱 </a></li>
			                                <li><a href="mailbox.html"> <i class="fa fa-file-text-o"></i> 草稿箱 <span class="label label-danger float-right">2</span></a></li>
			                                <li><a href="mailbox.html"> <i class="fa fa-trash-o"></i> 垃圾箱</a></li>
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
		                        <a href="###" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至草稿箱"><i class="fa fa-pencil"></i> 草稿</a>
		                    </div>
                    		<div class="clearfix"></div>
                		</div>
		            </div>
						<div id="inbox" style="display: none;" class="col-lg-9 animated fadeInRight">
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
				                   	 收件箱 (16)
				                </h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                	<!--翻页-->
				                    <div class="btn-group float-right">
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-left"></i></button>
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-right"></i></button>
				                    </div>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新 收件箱"><i class="fa fa-refresh"></i> 刷新</button>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标记为已读"><i class="fa fa-eye"></i> </button>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至垃圾箱"><i class="fa fa-trash-o"></i> </button>
				                </div>
				            </div>
			                <div class="mail-box">
				                <table class="table table-hover table-mail">
					                <tbody>

					                </tbody>
				                </table>
			                </div>
			            </div>
			        	<div id="outbox" style="display: none;" class="col-lg-9 animated fadeInRight">
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
				                   	 发件箱 (16)
				                </h2>
				                <div class="mail-tools tooltip-demo m-t-md">
				                	<!--翻页-->
				                    <div class="btn-group float-right">
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-left"></i></button>
				                        <button class="btn btn-white btn-sm"><i class="fa fa-arrow-right"></i></button>
				                    </div>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新 收件箱"><i class="fa fa-refresh"></i> 刷新</button>
				                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="移至垃圾箱"><i class="fa fa-trash-o"></i> </button>
				                </div>
				            </div>
			                <div class="mail-box">
				                <table class="table table-hover table-mail">
					                <tbody>
						                <tr class="unread">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Anna Smith</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Lorem ipsum dolor noretek imit set.</a></td>
						                    <td class=""><i class="fa fa-paperclip"></i></td>
						                    <td class="text-right mail-date">6.10 AM</td>
						                </tr>
						                <tr class="unread">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks" checked>
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Jack Nowak</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Aldus PageMaker including versions of Lorem Ipsum.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">8.22 PM</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Facebook</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Many desktop publishing packages and web page editors.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Jan 16</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Mailchip</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">There are many variations of passages of Lorem Ipsum.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Mar 22</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Alex T.</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Lorem ipsum dolor noretek imit set.</a></td>
						                    <td class=""><i class="fa fa-paperclip"></i></td>
						                    <td class="text-right mail-date">December 22</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Monica Ryther</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">The standard chunk of Lorem Ipsum used.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Jun 12</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Sandra Derick</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Contrary to popular belief.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">May 28</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Patrick Pertners</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">If you are going to use a passage of Lorem </a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">May 28</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Michael Fox</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Humour, or non-characteristic words etc.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Dec 9</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Damien Ritz</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Oor Lorem Ipsum is that it has a more-or-less normal.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Jun 11</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Anna Smith</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Lorem ipsum dolor noretek imit set.</a></td>
						                    <td class=""><i class="fa fa-paperclip"></i></td>
						                    <td class="text-right mail-date">6.10 AM</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Jack Nowak</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Aldus PageMaker including versions of Lorem Ipsum.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">8.22 PM</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Mailchip</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">There are many variations of passages of Lorem Ipsum.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Mar 22</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Alex T.</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Lorem ipsum dolor noretek imit set.</a></td>
						                    <td class=""><i class="fa fa-paperclip"></i></td>
						                    <td class="text-right mail-date">December 22</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Monica Ryther</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">The standard chunk of Lorem Ipsum used.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Jun 12</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Sandra Derick</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Contrary to popular belief.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">May 28</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Patrick Pertners</a> </td>
						                    <td class="mail-subject"><a href="mail_detail.html">If you are going to use a passage of Lorem </a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">May 28</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Michael Fox</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Humour, or non-characteristic words etc.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Dec 9</td>
						                </tr>
						                <tr class="read">
						                    <td class="check-mail">
						                        <input type="checkbox" class="i-checks">
						                    </td>
						                    <td class="mail-ontact"><a href="mail_detail.html">Damien Ritz</a></td>
						                    <td class="mail-subject"><a href="mail_detail.html">Oor Lorem Ipsum is that it has a more-or-less normal.</a></td>
						                    <td class=""></td>
						                    <td class="text-right mail-date">Jun 11</td>
						                </tr>
					                </tbody>
				                </table>
			                </div>
			            </div>
			        	<div id="draftbox" style="display: none;" class="col-lg-9 animated fadeInRight">
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
				                   	 草稿箱 (16)
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
