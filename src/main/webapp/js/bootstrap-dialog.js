(function($) {
    window.Bootstrap = function() {
        var tipHtml =
            '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
            '<div class="modal-dialog modal-sm">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
            '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
            '</div>' +
            '<div class="modal-body">' +
            '	<p>[Message]</p>' +
            '</div>' +
            '<div class="modal-footer">' +
            '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[CancelBtn]</button>' +
            '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[OkBtn]</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';

        var dialogHtml =
            '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
            '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
            '</div>' +
            '<div class="modal-body">' +

            '</div>' +
            '<div class="modal-footer">' +
            '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[CancelBtn]</button>' +
            '<button type="button" class="btn btn-primary ok">[OkBtn]</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
        var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
        var generateId = function() {
            var date = new Date();
            return 'modal' + date.valueOf();
        }

        return {
            tip: function(options) {
                if(typeof options == 'string') {
                    options = {
                        message: options
                    };
                }
                options = $.extend({}, {
                    title: "提示",
                    message: "提示内容",
                    timeout: 3000 //设置提示框多长时间自动消失（单位：毫秒）
                }, options || {});
                var modalId = generateId();
                var content = tipHtml.replace(reg, function(node, key) {
                    return {
                        Id: modalId,
                        Title: options.title,
                        Message: options.message
                    }[key];
                });

                $('body').append(content);

                var $modal = $('#' + modalId);

                $modal.modal({
                    backdrop: 'static'//用户点击模态框外部时不会关闭模态框
                });
                $modal.on('hide.bs.modal', function(e) {//模态框关闭时触发该事件
                    $('body').find('#' + modalId).remove();
                });
                $modal.find('.ok').hide();
                $modal.find('.cancel').hide();

                setTimeout(function(){
                    $modal.modal('hide');
                },options.timeout);
            },

            alert: function(options) {
                if(typeof options == 'string') {
                    options = {
                        message: options
                    };
                }

                options = $.extend({}, {
                    title: "提示",
                    message: "提示内容",
                    okValue: "确定"
                }, options || {});
                var modalId = generateId();
                var content = tipHtml.replace(reg, function(node, key) {
                    return {
                        Id: modalId,
                        Title: options.title,
                        Message: options.message,
                        OkBtn: options.okValue
                    }[key];
                });
                $('body').append(content);

                var $modal = $('#' + modalId);
                $modal.modal({
                    backdrop: 'static'//用户点击模态框外部时不会关闭模态框
                });
                $modal.on('hide.bs.modal', function(e) {//模态框关闭时触发该事件
                    $('body').find('#' + modalId).remove();
                });

                $modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                $modal.find('.cancel').hide();
            },

            confirm: function(options) {
                options = $.extend({}, {
                    title: "操作提示",
                    message: "提示内容",
                    okValue: "确定",
                    cancelValue: "取消"
                }, options || {});
                var modalId = generateId();
                var content = tipHtml.replace(reg, function(node, key) {
                    return {
                        Id: modalId,
                        Title: options.title,
                        Message: options.message,
                        OkBtn: options.okValue,
                        CancelBtn: options.cancelValue
                    }[key];
                });
                $('body').append(content);

                var $modal = $('#' + modalId);
                $modal.modal({
                    backdrop: 'static'//用户点击模态框外部时不会关闭模态框
                });
                $modal.on('hide.bs.modal', function(e) {//模态框关闭时触发该事件
                    $('body').find('#' + modalId).remove();
                });

                $modal.find('.ok').removeClass('btn-primary').addClass('btn-success');

                return {
                    id: modalId,
                    ok: function(callback) {//点击ok按钮时触发的方法
                        if(callback && callback instanceof Function) {
                            $modal.find('.ok').click(function() {
                                callback(true);
                            });
                        }
                    },
                    cancel: function(callback) {//点击cancel按钮时触发的方法
                        if(callback && callback instanceof Function) {
                            $modal.find('.cancel').click(function() {
                                callback(false);
                            });
                        }
                    },
                    hide: function(callback) {//模态框隐藏时触发的方法
                        if(callback && callback instanceof Function) {
                            $modal.on('hide.bs.modal', function(e) {
                                callback(e);
                            });
                        }
                    }
                };
            },

            dialog: function(options) {
                options = $.extend({}, {
                    title: '提示',
                    url: '',
                    okHide: false,
                    okValue: "确定",
                    cancelValue: "取消",
                    onOK:function() {},
                    onCancel: function() {}
                }, options || {});
                var modalId = generateId();

                var content = dialogHtml.replace(reg, function(node, key) {
                    return {
                        Id: modalId,
                        Title: options.title,
                        OkBtn: options.okValue,
                        CancelBtn: options.cancelValue
                    }[key];
                });
                $('body').append(content);

                var $modal = $('#' + modalId);

                $modal.modal({
                    backdrop: 'static'//用户点击模态框外部时不会关闭模态框
                });
                $modal.on('hide.bs.modal', function(e) {//模态框关闭时触发该事件
                    $('body').find('#' + modalId).remove();
                });

                $modal.find('.modal-body').load(options.url);

                if(options.okHide){
                    $modal.find('.ok').hide();
                    $modal.find('.cancel').removeClass('btn-default').addClass('btn-success');
                }else{
                    $modal.find('.ok').click(options.onOK);
                }
                $modal.find('.cancel').click(options.onCancel);

                return $modal;
            }
        }
    }();
})(jQuery);