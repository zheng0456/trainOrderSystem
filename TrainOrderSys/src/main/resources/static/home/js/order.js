;!function (window, undefined) {
    "use strict";
    var path = '', $, win, ready = {
        getPath: function () {
            var js = document.scripts || document.getElementsByTagName('script'), jsPath = js[js.length - 1].src;
            return path ? path : jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
        }, type: ['dialog', 'page', 'iframe', 'loading', 'tips']
    };
    window.layer = {
        v: '1.8.5',
        ie6: !!window.ActiveXObject && !window.XMLHttpRequest,
        index: 0,
        path: ready.getPath(),
        use: function (module, callback) {
            var i = 0, head = $('head')[0];
            var module = module.replace(/\s/g, '');
            var iscss = /\.css$/.test(module);
            var node = document.createElement(iscss ? 'link' : 'script');
            var id = module.replace(/\.|\//g, '');
            if (iscss) {
                node.type = 'text/css';
                node.rel = 'stylesheet';
            }
            node[iscss ? 'href' : 'src'] = /^http:\/\//.test(module) ? module : layer.path + module;
            node.id = id;
            if (!$('#' + id)[0]) {
                head.appendChild(node);
            }
            if (callback) {
                if (document.all) {
                    $(node).ready(callback);
                } else {
                    $(node).load(callback);
                }
            }
        },
        alert: function (msg, icon, fn, yes) {
            var isfn = (typeof fn === 'function'),
                conf = {dialog: {msg: msg, type: icon, yes: isfn ? fn : yes}, area: ['auto', 'auto']};
            isfn || (conf.title = fn);
            return $.layer(conf);
        },
        confirm: function (msg, yes, fn, no) {
            var isfn = (typeof fn === 'function'),
                conf = {dialog: {msg: msg, type: 4, btns: 2, yes: yes, no: isfn ? fn : no}};
            isfn || (conf.title = fn);
            return $.layer(conf);
        },
        msg: function (msg, time, parme, end) {
            var conf = {
                title: false,
                closeBtn: false,
                time: time === undefined ? 2 : time,
                dialog: {msg: (msg === '' || msg === undefined) ? '&nbsp;' : msg},
                end: end
            };
            if (typeof parme === 'object') {
                conf.dialog.type = parme.type;
                conf.shade = parme.shade;
                conf.shift = parme.rate;
            } else if (typeof parme === 'function') {
                conf.end = parme
            } else {
                conf.dialog.type = parme;
            }
            return $.layer(conf);
        },
        load: function (parme, icon) {
            if (typeof parme === 'string') {
                return layer.msg(parme, icon || 0, 16);
            } else {
                return $.layer({
                    time: parme,
                    loading: {type: icon},
                    bgcolor: icon ? '#fff' : '',
                    shade: icon ? [0.1, '#000'] : [0],
                    border: (icon === 3 || !icon) ? [0] : [6, 0.3, '#000'],
                    type: 3,
                    title: ['', false],
                    closeBtn: [0, false]
                });
            }
        },
        tips: function (html, follow, parme, maxWidth, guide, style) {
            var conf = {
                type: 4, shade: false, success: function (layero) {
                    if (!this.closeBtn) {
                        layero.find('.xubox_tips').css({'padding-right': 10});
                    }
                }, bgcolor: '', tips: {msg: html, follow: follow}
            };
            conf.time = typeof parme === 'object' ? parme.time : (parme | 0);
            parme = parme || {};
            conf.closeBtn = parme.closeBtn || false
            conf.maxWidth = parme.maxWidth || maxWidth;
            conf.tips.guide = parme.guide || guide;
            conf.tips.style = parme.style || style;
            conf.tips.more = parme.more;
            return $.layer(conf);
        }
    };
    var doms = ['xubox_layer', 'xubox_iframe', '.xubox_title', '.xubox_text', '.xubox_page', '.xubox_main'];
    var Class = function (setings) {
        var that = this, config = that.config;
        layer.index++;
        that.index = layer.index;
        that.config = $.extend({}, config, setings);
        that.config.dialog = $.extend({}, config.dialog, setings.dialog);
        that.config.page = $.extend({}, config.page, setings.page);
        that.config.iframe = $.extend({}, config.iframe, setings.iframe);
        that.config.loading = $.extend({}, config.loading, setings.loading);
        that.config.tips = $.extend({}, config.tips, setings.tips);
        that.creat();
    };
    Class.pt = Class.prototype;
    Class.pt.config = {
        type: 0,
        shade: [0.3, '#000'],
        fix: true,
        move: '.xubox_title',
        title: '&#x4FE1;&#x606F;',
        offset: ['', '50%'],
        area: ['310px', 'auto'],
        closeBtn: [0, true],
        time: 0,
        bgcolor: '#fff',
        border: [6, 0.3, '#000'],
        zIndex: 19891014,
        maxWidth: 400,
        dialog: {
            btns: 1, btn: ['&#x786E;&#x5B9A;', '&#x53D6;&#x6D88;'], type: 8, msg: '', yes: function (index) {
                layer.close(index);
            }, no: function (index) {
                layer.close(index);
            }
        },
        page: {dom: '#xulayer', html: '', url: ''},
        iframe: {src: 'http://sentsin.com', scrolling: 'auto'},
        loading: {type: 0},
        tips: {
            msg: '',
            follow: '',
            guide: 0,
            isGuide: true,
            style: ['background-color:#FF9900; color:#fff;', '#FF9900']
        },
        success: function (layer) {
        },
        close: function (index) {
            layer.close(index);
        },
        end: function () {
        }
    };
    Class.pt.space = function (html) {
        var that = this, html = html || '', times = that.index, config = that.config, dialog = config.dialog,
            ico = dialog.type === -1 ? '' : '<span class="xubox_msg xulayer_png32 xubox_msgico xubox_msgtype' + dialog.type + '"></span>',
            frame = ['<div class="xubox_dialog">' + ico + '<span class="xubox_msg xubox_text" style="' + (ico ? '' : 'padding-left:20px') + '">' + dialog.msg + '</span></div>', '<div class="xubox_page">' + html + '</div>', '<iframe scrolling="' + config.iframe.scrolling + '" allowtransparency="true" id="' + doms[1] + '' + times + '" name="' + doms[1] + '' + times + '" onload="this.className=\'' + doms[1] + '\'" class="' + doms[1] + '" frameborder="0" src="' + config.iframe.src + '"></iframe>', '<span class="xubox_loading xubox_loading_' + config.loading.type + '"></span>', '<div class="xubox_tips" style="' + config.tips.style[0] + '"><div class="xubox_tipsMsg">' + config.tips.msg + '</div><i class="layerTipsG"></i></div>'],
            shade = '', border = '', zIndex = config.zIndex + times,
            shadeStyle = 'z-index:' + zIndex + '; background-color:' + config.shade[1] + '; opacity:' + config.shade[0] + '; filter:alpha(opacity=' + config.shade[0] * 100 + ');';
        config.shade[0] && (shade = '<div times="' + times + '" id="xubox_shade' + times + '" class="xubox_shade" style="' + shadeStyle + '"></div>');
        config.zIndex = zIndex;
        var title = '', closebtn = '',
            borderStyle = "z-index:" + (zIndex - 1) + ";  background-color: " + config.border[2] + "; opacity:" + config.border[1] + "; filter:alpha(opacity=" + config.border[1] * 100 + "); top:-" + config.border[0] + "px; left:-" + config.border[0] + "px;";
        config.border[0] && (border = '<div id="xubox_border' + times + '" class="xubox_border" style="' + borderStyle + '"></div>');
        if (config.maxmin && (config.type === 1 || config.type === 2) && (!/^\d+%$/.test(config.area[0]) || !/^\d+%$/.test(config.area[1]))) {
            closebtn = '<a class="xubox_min" href="javascript:;"><cite></cite></a><a class="xubox_max xulayer_png32" href="javascript:;"></a>';
        }
        config.closeBtn[1] && (closebtn += '<a class="xubox_close xulayer_png32 xubox_close' + config.closeBtn[0] + '" href="javascript:;" style="' + (config.type === 4 ? 'position:absolute; right:-3px; _right:7px; top:-4px;' : '') + '"></a>');
        var titype = typeof config.title === 'object';
        config.title && (title = '<div class="xubox_title" style="' + (titype ? config.title[1] : '') + '"><em>' + (titype ? config.title[0] : config.title) + '</em></div>');
        return [shade, '<div times="' + times + '" showtime="' + config.time + '" style="z-index:' + zIndex + '" id="' + doms[0] + '' + times
        + '" class="' + doms[0] + '">'
        + '<div style="background-color:' + config.bgcolor + '; z-index:' + zIndex + '" class="xubox_main">'
        + frame[config.type]
        + title
        + '<span class="xubox_setwin">' + closebtn + '</span>'
        + '<span class="xubox_botton"></span>'
        + '</div>' + border + '</div>'];
    };
    Class.pt.creat = function () {
        var that = this, space = '', config = that.config, dialog = config.dialog, times = that.index;
        var page = config.page, body = $("body"), setSpace = function (html) {
            var html = html || '';
            space = that.space(html);
            body.append($(space[0]));
        };
        switch (config.type) {
            case 0:
                config.title || (config.area = ['auto', 'auto']);
                $('.xubox_dialog')[0] && layer.close($('.xubox_dialog').parents('.' + doms[0]).attr('times'));
                break;
            case 1:
                if (page.html !== '') {
                    setSpace('<div class="xuboxPageHtml">' + page.html + '</div>');
                    body.append($(space[1]));
                } else if (page.url !== '') {
                    setSpace('<div class="xuboxPageHtml" id="xuboxPageHtml' + times + '">' + page.html + '</div>');
                    body.append($(space[1]));
                    $.get(page.url, function (datas) {
                        $('#xuboxPageHtml' + times).html(datas.toString());
                        page.ok && page.ok(datas);
                    });
                } else {
                    if ($(page.dom).parents(doms[4]).length == 0) {
                        setSpace();
                        $(page.dom).show().wrap($(space[1]));
                    } else {
                        return;
                    }
                }
                break;
            case 3:
                config.title = false;
                config.area = ['auto', 'auto'];
                config.closeBtn = ['', false];
                $('.xubox_loading')[0] && layer.closeLoad();
                break;
            case 4:
                config.title = false;
                config.area = ['auto', 'auto'];
                config.fix = false;
                config.border = [0];
                config.tips.more || layer.closeTips();
                break;
        }
        ;
        if (config.type !== 1) {
            setSpace();
            body.append($(space[1]));
        }
        var layerE = that.layerE = $('#' + doms[0] + times);
        layerE.css({width: config.area[0], height: config.area[1]});
        config.fix || layerE.css({position: 'absolute'});
        if (config.title && (config.type !== 3 || config.type !== 4)) {
            var confbtn = config.type === 0 ? dialog : config, layerBtn = layerE.find('.xubox_botton');
            confbtn.btn = config.btn || dialog.btn;
            switch (confbtn.btns) {
                case 0:
                    layerBtn.html('').hide();
                    break;
                case 1:
                    layerBtn.html('<a href="javascript:;" class="xubox_yes xubox_botton1">' + confbtn.btn[0] + '</a>');
                    break;
                case 2:
                    layerBtn.html('<a href="javascript:;" class="xubox_yes xubox_botton2">' + confbtn.btn[0] + '</a>' + '<a href="javascript:;" class="xubox_no xubox_botton3">' + confbtn.btn[1] + '</a>');
                    break;
            }
        }
        if (layerE.css('left') === 'auto') {
            layerE.hide();
            setTimeout(function () {
                layerE.show();
                that.set(times);
            }, 500);
        } else {
            that.set(times);
        }
        config.time <= 0 || that.autoclose();
        that.callback();
    };
    ready.fade = function (obj, time, opa) {
        obj.css({opacity: 0}).animate({opacity: opa}, time);
    };
    Class.pt.offset = function () {
        var that = this, config = that.config, layerE = that.layerE, laywid = layerE.outerHeight();
        if (config.offset[0] === '' && laywid < win.height()) {
            that.offsetTop = (win.height() - laywid - 2 * config.border[0]) / 2;
        } else if (config.offset[0].indexOf("px") != -1) {
            that.offsetTop = parseFloat(config.offset[0]);
        } else {
            that.offsetTop = parseFloat(config.offset[0] || 0) / 100 * win.height();
        }
        that.offsetTop = that.offsetTop + config.border[0] + (config.fix ? 0 : win.scrollTop());
        if (config.offset[1].indexOf("px") != -1) {
            that.offsetLeft = parseFloat(config.offset[1]) + config.border[0];
        } else {
            config.offset[1] = config.offset[1] === '' ? '50%' : config.offset[1];
            if (config.offset[1] === '50%') {
                that.offsetLeft = config.offset[1];
            } else {
                that.offsetLeft = parseFloat(config.offset[1]) / 100 * win.width() + config.border[0];
            }
        }
        ;
    };
    Class.pt.set = function (times) {
        var that = this;
        var config = that.config;
        var dialog = config.dialog;
        var page = config.page;
        var loading = config.loading;
        var layerE = that.layerE;
        var layerTitle = layerE.find(doms[2]);
        that.autoArea(times);
        if (config.title) {
            if (config.type === 0) {
                layer.ie6 && layerTitle.css({width: layerE.outerWidth()});
            }
        } else {
            config.type !== 4 && layerE.find('.xubox_close').addClass('xubox_close1');
        }
        ;layerE.attr({'type': ready.type[config.type]});
        that.offset();
        if (config.type !== 4) {
            if (config.shift && !layer.ie6) {
                if (typeof config.shift === 'object') {
                    that.shift(config.shift[0], config.shift[1] || 500, config.shift[2]);
                } else {
                    that.shift(config.shift, 500);
                }
            } else {
                layerE.css({top: that.offsetTop, left: that.offsetLeft});
            }
        }
        switch (config.type) {
            case 0:
                layerE.find(doms[5]).css({'background-color': '#fff'});
                if (config.title) {
                    layerE.find(doms[3]).css({paddingTop: 18 + layerTitle.outerHeight()});
                } else {
                    layerE.find('.xubox_msgico').css({top: 8});
                    layerE.find(doms[3]).css({marginTop: 11});
                }
                break;
            case 1:
                layerE.find(page.dom).addClass('layer_pageContent');
                config.shade[0] && layerE.css({zIndex: config.zIndex + 1});
                config.title && layerE.find(doms[4]).css({top: layerTitle.outerHeight()});
                break;
            case 2:
                var iframe = layerE.find('.' + doms[1]), heg = layerE.height();
                iframe.addClass('xubox_load').css({width: layerE.width()});
                config.title ? iframe.css({
                    top: layerTitle.height(),
                    height: heg - layerTitle.height()
                }) : iframe.css({top: 0, height: heg});
                layer.ie6 && iframe.attr('src', config.iframe.src);
                break;
            case 4:
                var layArea = [0, layerE.outerHeight()], fow = $(config.tips.follow), fowo = {
                    width: fow.outerWidth(),
                    height: fow.outerHeight(),
                    top: fow.offset().top,
                    left: fow.offset().left
                }, tipsG = layerE.find('.layerTipsG');
                config.tips.isGuide || tipsG.remove();
                layerE.outerWidth() > config.maxWidth && layerE.width(config.maxWidth);
                fowo.tipColor = config.tips.style[1];
                layArea[0] = layerE.outerWidth();
                fowo.autoLeft = function () {
                    if (fowo.left + layArea[0] - win.width() > 0) {
                        fowo.tipLeft = fowo.left + fowo.width - layArea[0];
                        tipsG.css({right: 12, left: 'auto'});
                    } else {
                        fowo.tipLeft = fowo.left;
                    }
                    ;
                };
                fowo.where = [function () {
                    fowo.autoLeft();
                    fowo.tipTop = fowo.top - layArea[1] - 10;
                    tipsG.removeClass('layerTipsB').addClass('layerTipsT').css({'border-right-color': fowo.tipColor});
                }, function () {
                    fowo.tipLeft = fowo.left + fowo.width + 10;
                    fowo.tipTop = fowo.top;
                    tipsG.removeClass('layerTipsL').addClass('layerTipsR').css({'border-bottom-color': fowo.tipColor});
                }, function () {
                    fowo.autoLeft();
                    fowo.tipTop = fowo.top + fowo.height + 10;
                    tipsG.removeClass('layerTipsT').addClass('layerTipsB').css({'border-right-color': fowo.tipColor});
                }, function () {
                    fowo.tipLeft = fowo.left - layArea[0] + 10;
                    fowo.tipTop = fowo.top;
                    tipsG.removeClass('layerTipsR').addClass('layerTipsL').css({'border-bottom-color': fowo.tipColor});
                }];
                fowo.where[config.tips.guide]();
                if (config.tips.guide === 0) {
                    fowo.top - (win.scrollTop() + layArea[1] + 8 * 2) < 0 && fowo.where[2]();
                } else if (config.tips.guide === 1) {
                    win.width() - (fowo.left + fowo.width + layArea[0] + 8 * 2) > 0 || fowo.where[3]()
                } else if (config.tips.guide === 2) {
                    (fowo.top - win.scrollTop() + fowo.height + layArea[1] + 8 * 2) - win.height() > 0 && fowo.where[0]();
                } else if (config.tips.guide === 3) {
                    layArea[0] + 8 * 2 - fowo.left > 0 && fowo.where[1]()
                } else if (config.tips.guide === 4) {
                }
                layerE.css({left: fowo.tipLeft, top: fowo.tipTop});
                break;
        }
        ;
        if (config.fadeIn) {
            ready.fade(layerE, config.fadeIn, 1);
            ready.fade($('#xubox_shade' + times), config.fadeIn, config.shade[0]);
        }
        if (config.fix && config.offset[0] === '' && !config.shift) {
            win.on('resize', function () {
                layerE.css({top: (win.height() - layerE.outerHeight()) / 2});
            });
        }
        that.move();
    };
    Class.pt.shift = function (type, rate, stop) {
        var that = this, config = that.config;
        var layerE = that.layerE;
        var cutWth = 0, ww = win.width();
        var wh = win.height() + (config.fix ? 0 : win.scrollTop());
        if (config.offset[1] == '50%' || config.offset[1] == '') {
            cutWth = layerE.outerWidth() / 2;
        } else {
            cutWth = layerE.outerWidth();
        }
        var anim = {
            t: {top: that.offsetTop},
            b: {top: wh - layerE.outerHeight() - config.border[0]},
            cl: cutWth + config.border[0],
            ct: -layerE.outerHeight(),
            cr: ww - cutWth - config.border[0]
        };
        switch (type) {
            case'left-top':
                layerE.css({left: anim.cl, top: anim.ct}).animate(anim.t, rate);
                break;
            case'top':
                layerE.css({top: anim.ct}).animate(anim.t, rate);
                break;
            case'right-top':
                layerE.css({left: anim.cr, top: anim.ct}).animate(anim.t, rate);
                break;
            case'right-bottom':
                layerE.css({left: anim.cr, top: wh}).animate(stop ? anim.t : anim.b, rate);
                break;
            case'bottom':
                layerE.css({top: wh}).animate(stop ? anim.t : anim.b, rate);
                break;
            case'left-bottom':
                layerE.css({left: anim.cl, top: wh}).animate(stop ? anim.t : anim.b, rate);
                break;
            case'left':
                layerE.css({left: -layerE.outerWidth()}).animate({left: that.offsetLeft}, rate);
                break;
        }
    };
    Class.pt.autoArea = function (times) {
        var that = this, times = times || that.index, config = that.config, page = config.page;
        var layerE = $('#' + doms[0] + times), layerTitle = layerE.find(doms[2]), layerMian = layerE.find(doms[5]);
        var titHeight = config.title ? layerTitle.innerHeight() : 0, outHeight, btnHeight = 0;
        if (config.area[0] === 'auto' && layerMian.outerWidth() >= config.maxWidth) {
            layerE.css({width: config.maxWidth});
        }
        switch (config.type) {
            case 0:
                var aBtn = layerE.find('.xubox_botton>a');
                outHeight = layerE.find(doms[3]).outerHeight() + 20;
                if (aBtn.length > 0) {
                    btnHeight = aBtn.outerHeight() + 20;
                }
                break;
            case 1:
                var layerPage = layerE.find(doms[4]);
                outHeight = $(page.dom).outerHeight();
                config.area[0] === 'auto' && layerE.css({width: layerPage.outerWidth()});
                if (page.html !== '' || page.url !== '') {
                    outHeight = layerPage.outerHeight();
                }
                break;
            case 2:
                layerE.find('iframe').css({
                    width: layerE.outerWidth(),
                    height: layerE.outerHeight() - (config.title ? layerTitle.innerHeight() : 0)
                });
                break;
            case 3:
                var load = layerE.find(".xubox_loading");
                outHeight = load.outerHeight();
                layerMian.css({width: load.width()});
                break;
        }
        ;(config.area[1] === 'auto') && layerMian.css({height: titHeight + outHeight + btnHeight});
        $('#xubox_border' + times).css({
            width: layerE.outerWidth() + 2 * config.border[0],
            height: layerE.outerHeight() + 2 * config.border[0]
        });
        (layer.ie6 && config.area[0] !== 'auto') && layerMian.css({width: layerE.outerWidth()});
        (config.offset[1] === '50%' || config.offset[1] == '') && (config.type !== 4) ? layerE.css({marginLeft: -layerE.outerWidth() / 2}) : layerE.css({marginLeft: 0});
    };
    Class.pt.move = function () {
        var that = this, config = that.config, conf = {
            setY: 0, moveLayer: function () {
                if (parseInt(conf.layerE.css('margin-left')) == 0) {
                    var lefts = parseInt(conf.move.css('left'));
                } else {
                    var lefts = parseInt(conf.move.css('left')) + (-parseInt(conf.layerE.css('margin-left')))
                }
                if (conf.layerE.css('position') !== 'fixed') {
                    lefts = lefts - conf.layerE.parent().offset().left;
                    conf.setY = 0
                }
                conf.layerE.css({left: lefts, top: parseInt(conf.move.css('top')) - conf.setY});
            }
        };
        var movedom = that.layerE.find(config.move);
        config.move && movedom.attr('move', 'ok');
        config.move ? movedom.css({cursor: 'move'}) : movedom.css({cursor: 'auto'});
        $(config.move).on('mousedown', function (M) {
            M.preventDefault();
            if ($(this).attr('move') === 'ok') {
                conf.ismove = true;
                conf.layerE = $(this).parents('.' + doms[0]);
                var xx = conf.layerE.offset().left, yy = conf.layerE.offset().top, ww = conf.layerE.width() - 6,
                    hh = conf.layerE.height() - 6;
                if (!$('#xubox_moves')[0]) {
                    $('body').append('<div id="xubox_moves" class="xubox_moves" style="left:' + xx + 'px; top:' + yy + 'px; width:' + ww + 'px; height:' + hh + 'px; z-index:2147483584"></div>');
                }
                conf.move = $('#xubox_moves');
                config.moveType && conf.move.css({opacity: 0});
                conf.moveX = M.pageX - conf.move.position().left;
                conf.moveY = M.pageY - conf.move.position().top;
                conf.layerE.css('position') !== 'fixed' || (conf.setY = win.scrollTop());
            }
        });
        $(document).mousemove(function (M) {
            if (conf.ismove) {
                var offsetX = M.pageX - conf.moveX, offsetY = M.pageY - conf.moveY;
                M.preventDefault();
                if (!config.moveOut) {
                    conf.setY = win.scrollTop();
                    var setRig = win.width() - conf.move.outerWidth() - config.border[0],
                        setTop = config.border[0] + conf.setY;
                    offsetX < config.border[0] && (offsetX = config.border[0]);
                    offsetX > setRig && (offsetX = setRig);
                    offsetY < setTop && (offsetY = setTop);
                    offsetY > win.height() - conf.move.outerHeight() - config.border[0] + conf.setY && (offsetY = win.height() - conf.move.outerHeight() - config.border[0] + conf.setY);
                }
                conf.move.css({left: offsetX, top: offsetY});
                config.moveType && conf.moveLayer();
                offsetX = null;
                offsetY = null;
                setRig = null;
                setTop = null
            }
        }).mouseup(function () {
            try {
                if (conf.ismove) {
                    conf.moveLayer();
                    conf.move.remove();
                }
                conf.ismove = false;
            } catch (e) {
                conf.ismove = false;
            }
            config.moveEnd && config.moveEnd();
        });
    };
    Class.pt.autoclose = function () {
        var that = this, time = that.config.time, maxLoad = function () {
            time--;
            if (time === 0) {
                layer.close(that.index);
                clearInterval(that.autotime);
            }
        };
        that.autotime = setInterval(maxLoad, 1000);
    };
    ready.config = {end: {}};
    Class.pt.callback = function () {
        var that = this, layerE = that.layerE, config = that.config, dialog = config.dialog;
        that.openLayer();
        that.config.success(layerE);
        layer.ie6 && that.IE6(layerE);
        layerE.find('.xubox_close').on('click', function () {
            config.close(that.index);
            layer.close(that.index);
        });
        layerE.find('.xubox_yes').on('click', function () {
            config.yes ? config.yes(that.index) : dialog.yes(that.index);
        });
        layerE.find('.xubox_no').on('click', function () {
            config.no ? config.no(that.index) : dialog.no(that.index);
            layer.close(that.index);
        });
        if (that.config.shadeClose) {
            $('#xubox_shade' + that.index).on('click', function () {
                layer.close(that.index);
            });
        }
        layerE.find('.xubox_min').on('click', function () {
            layer.min(that.index, config);
            config.min && config.min(layerE);
        });
        layerE.find('.xubox_max').on('click', function () {
            if ($(this).hasClass('xubox_maxmin')) {
                layer.restore(that.index);
                config.restore && config.restore(layerE);
            } else {
                layer.full(that.index, config);
                config.full && config.full(layerE);
            }
        });
        ready.config.end[that.index] = config.end;
    };
    ready.reselect = function () {
        $.each($('select'), function (index, value) {
            var sthis = $(this);
            if (!sthis.parents('.' + doms[0])[0]) {
                (sthis.attr('layer') == 1 && $('.' + doms[0]).length < 1) && sthis.removeAttr('layer').show();
            }
            sthis = null;
        });
    };
    Class.pt.IE6 = function (layerE) {
        var that = this;
        var _ieTop = layerE.offset().top;
        if (that.config.fix) {
            var ie6Fix = function () {
                layerE.css({top: win.scrollTop() + _ieTop});
            };
        } else {
            var ie6Fix = function () {
                layerE.css({top: _ieTop});
            };
        }
        ie6Fix();
        win.scroll(ie6Fix);
        $.each($('select'), function (index, value) {
            var sthis = $(this);
            if (!sthis.parents('.' + doms[0])[0]) {
                sthis.css('display') == 'none' || sthis.attr({'layer': '1'}).hide();
            }
            sthis = null;
        });
    };
    Class.pt.openLayer = function () {
        var that = this, layerE = that.layerE;
        layer.autoArea = function (index) {
            return that.autoArea(index);
        };
        layer.shift = function (type, rate, stop) {
            that.shift(type, rate, stop);
        };
        layer.setMove = function () {
            return that.move();
        };
        layer.zIndex = that.config.zIndex;
        layer.setTop = function (layerNow) {
            var setZindex = function () {
                layer.zIndex++;
                layerNow.css('z-index', layer.zIndex + 1);
            };
            layer.zIndex = parseInt(layerNow[0].style.zIndex);
            layerNow.on('mousedown', setZindex);
            return layer.zIndex;
        };
    };
    ready.isauto = function (layero, options, offset) {
        options.area[0] === 'auto' && (options.area[0] = layero.outerWidth());
        options.area[1] === 'auto' && (options.area[1] = layero.outerHeight());
        layero.attr({area: options.area + ',' + offset});
        layero.find('.xubox_max').addClass('xubox_maxmin');
    };
    ready.rescollbar = function (index) {
        if (doms.html.attr('layer-full') == index) {
            if (doms.html[0].style.removeProperty) {
                doms.html[0].style.removeProperty('overflow');
            } else {
                doms.html[0].style.removeAttribute('overflow');
            }
            doms.html.removeAttr('layer-full');
        }
    };
    layer.getIndex = function (selector) {
        return $(selector).parents('.' + doms[0]).attr('times');
    };
    layer.getChildFrame = function (selector, index) {
        index = index || $('.' + doms[1]).parents('.' + doms[0]).attr('times');
        return $('#' + doms[0] + index).find('.' + doms[1]).contents().find(selector);
    };
    layer.getFrameIndex = function (name) {
        return $(name ? '#' + name : '.' + doms[1]).parents('.' + doms[0]).attr('times');
    };
    layer.iframeAuto = function (index) {
        index = index || $('.' + doms[1]).parents('.' + doms[0]).attr('times');
        var heg = layer.getChildFrame('body', index).outerHeight(), layero = $('#' + doms[0] + index),
            tit = layero.find(doms[2]), titHt = 0;
        tit && (titHt = tit.height());
        layero.css({height: heg + titHt});
        var bs = -parseInt($('#xubox_border' + index).css('top'));
        $('#xubox_border' + index).css({height: heg + 2 * bs + titHt});
        $('#' + doms[1] + index).css({height: heg});
    };
    layer.iframeSrc = function (index, url) {
        $('#' + doms[0] + index).find('iframe').attr('src', url);
    };
    layer.area = function (index, options) {
        var layero = [$('#' + doms[0] + index), $('#xubox_border' + index)], type = layero[0].attr('type'),
            main = layero[0].find(doms[5]), title = layero[0].find(doms[2]);
        if (type === ready.type[1] || type === ready.type[2]) {
            layero[0].css(options);
            main.css({width: options.width, height: options.height});
            if (type === ready.type[2]) {
                var iframe = layero[0].find('iframe');
                iframe.css({
                    width: options.width,
                    height: title ? options.height - title.innerHeight() : options.height
                });
            }
            if (layero[0].css('margin-left') !== '0px') {
                options.hasOwnProperty('top') && layero[0].css({top: options.top - (layero[1][0] ? parseFloat(layero[1].css('top')) : 0)});
                options.hasOwnProperty('left') && layero[0].css({left: options.left + layero[0].outerWidth() / 2 - (layero[1][0] ? parseFloat(layero[1].css('left')) : 0)});
                layero[0].css({marginLeft: -layero[0].outerWidth() / 2});
            }
            if (layero[1][0]) {
                layero[1].css({
                    width: parseFloat(options.width) - 2 * parseFloat(layero[1].css('left')),
                    height: parseFloat(options.height) - 2 * parseFloat(layero[1].css('top'))
                });
            }
        }
    };
    layer.min = function (index, options) {
        var layero = $('#' + doms[0] + index),
            offset = [layero.position().top, layero.position().left + parseFloat(layero.css('margin-left'))];
        ready.isauto(layero, options, offset);
        layer.area(index, {width: 180, height: 35});
        layero.find('.xubox_min').hide();
        layero.attr('type') === 'page' && layero.find(doms[4]).hide();
        ready.rescollbar(index);
    };
    layer.restore = function (index) {
        var layero = $('#' + doms[0] + index), area = layero.attr('area').split(',');
        var type = layero.attr('type');
        layer.area(index, {
            width: parseFloat(area[0]),
            height: parseFloat(area[1]),
            top: parseFloat(area[2]),
            left: parseFloat(area[3])
        });
        layero.find('.xubox_max').removeClass('xubox_maxmin');
        layero.find('.xubox_min').show();
        layero.attr('type') === 'page' && layero.find(doms[4]).show();
        ready.rescollbar(index);
    };
    layer.full = function (index, options) {
        var layero = $('#' + doms[0] + index), borders = options.border[0] * 2 || 6, timer;
        var offset = [layero.position().top, layero.position().left + parseFloat(layero.css('margin-left'))];
        ready.isauto(layero, options, offset);
        if (!doms.html.attr('layer-full')) {
            doms.html.css('overflow', 'hidden').attr('layer-full', index);
        }
        clearTimeout(timer);
        timer = setTimeout(function () {
            layer.area(index, {
                top: layero.css('position') === 'fixed' ? 0 : win.scrollTop(),
                left: layero.css('position') === 'fixed' ? 0 : win.scrollLeft(),
                width: win.width() - borders,
                height: win.height() - borders
            });
        }, 100);
    };
    layer.title = function (name, index) {
        var title = $('#' + doms[0] + (index || layer.index)).find('.xubox_title>em');
        title.html(name);
    };
    layer.close = function (index) {
        var layero = $('#' + doms[0] + index), type = layero.attr('type'),
            shadeNow = $('#xubox_moves, #xubox_shade' + index);
        if (!layero[0]) {
            return;
        }
        if (type == ready.type[1]) {
            if (layero.find('.xuboxPageHtml')[0]) {
                layero[0].innerHTML = '';
                layero.remove();
            } else {
                layero.find('.xubox_setwin,.xubox_close,.xubox_botton,.xubox_title,.xubox_border').remove();
                for (var i = 0; i < 3; i++) {
                    layero.find('.layer_pageContent').unwrap().hide();
                }
            }
        } else {
            layero[0].innerHTML = '';
            layero.remove();
        }
        shadeNow.remove();
        layer.ie6 && ready.reselect();
        ready.rescollbar(index);
        typeof ready.config.end[index] === 'function' && ready.config.end[index]();
        delete ready.config.end[index];
    };
    layer.closeLoad = function () {
        layer.close($('.xubox_loading').parents('.' + doms[0]).attr('times'));
    };
    layer.closeTips = function () {
        layer.closeAll('tips');
    };
    layer.closeAll = function (type) {
        $.each($('.' + doms[0]), function () {
            var othis = $(this);
            var is = type ? (othis.attr('type') === type) : 1;
            if (is) {
                layer.close(othis.attr('times'));
            }
            is = null;
        });
    };
    ready.run = function () {
        $ = jQuery;
        win = $(window);
        doms.html = $('html');
        $.layer = function (deliver) {
            var o = new Class(deliver);
            return o.index;
        };
    };
    if ("function" === typeof define) {
        define(function () {
            ready.run();
            return layer;
        });
    } else {
        ready.run();
    }
}(window);
;jQuery.extend({
    TN_checkName: function (name) {
        name = name.replace(/(^\s*)/g, "");
        if (name == '') {
            return 2;
        } else if (this.TN_strlen(name) > 20 || this.TN_strlen(name) < 4) {
            return 3;
        } else {
            var expression = new RegExp("^[\u4E00-\u9FA5A-Za-z ]+$");
            if (expression.test(name)) {
                return 1;
            } else {
                return 4;
            }
        }
    }, TN_checkNameForTicket2: function (name) {
        name = name.replace(/(^\s*)/g, "");
        if (name == '') {
            return 2;
        } else if (/(\u5148\u751f)|(\u5c0f\u59d0)|(\u5973\u58eb)|(\u8001\u5e08)/.test(name)) {
            return 3;
        } else {
            var expression = new RegExp("^[\u4E00-\u9FA5]+$");
            if (expression.test(name)) {
                return 1;
            } else {
                return 4;
            }
        }
    }, TN_checkNameForTicketPassager: function (name, idType) {
        name = $.trim(name);
        if (name == '') {
            return 2;
        } else {
            if (idType == '1') {
                if (!/^[a-zA-Z·.．\u3400-\u9FFF]+$/.test(name)) {
                    return 4;
                }
            } else {
                if (idType == '2') {
                    if (!/^[a-z A-Z·.．\u3400-\u9FFF\-]+$/.test(name) || /^[-]+$/.test(name)) {
                        return 4;
                    }
                } else {
                    if (!/^[a-z A-Z·.．\u3400-\u9FFF]+$/.test(name)) {
                        return 4;
                    }
                }
            }
        }
        return 1;
    }, TN_checkTel: function (tel) {
        if (tel == '') {
            return 2;
        } else if (new RegExp("^((13[0-9])|(15[0-35-9])|(18[0-9])|145|147|170|177|178)[0-9]{8,8}$").test(tel) == false) {
            return 3;
        } else {
            return 1;
        }
    }, TN_checkPhone: function (area_code, phone) {
        if (area_code != '' || phone != '') {
            var reg1 = /^\d{3,4}$/;
            var reg2 = /^\d{7,8}$/;
            if (!reg1.test(area_code)) {
                return 2;
            }
            if (!reg2.test(phone)) {
                return 3;
            }
        }
        return 1;
    }, TN_checkAreaCode: function (area_code) {
        if (area_code != '') {
            var reg1 = /^\d{3,4}$/;
            if (!reg1.test(area_code)) {
                return 2;
            }
        }
        return 1;
    }, TN_checkTelPhone: function (phone) {
        if (phone != '') {
            var reg2 = /^\d{7,8}$/;
            if (!reg2.test(phone)) {
                return 2;
            }
        }
        return 1;
    }, TN_checkEmail: function (email) {
        if (email != '') {
            var patrn = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            if (!patrn.exec(email)) {
                return 3;
            }
        } else {
            return 2
        }
        return 1;
    }, TN_checkPostcode: function (postcode) {
        var expression = new RegExp("^[0-9]{6,6}$");
        if (expression.test(postcode) == true) {
            return 1;
        } else if (postcode != '') {
            return 3;
        } else {
            return 2;
        }
    }, TN_checkHKMacao: function (id) {
        var pat;
        if (!id) {
            return 2;
        }
        pat = /^[a-zA-Z0-9]{2,21}$/;
        if (pat.test(id)) {
            return 1;
        } else {
            return 3;
        }
    }, TN_checkTW: function (id) {
        var pat1 = /^[0-9]{8}$/;
        var pat2 = /^[0-9]{10}$/;
        if (!id) {
            return 2;
        }
        if (pat1.test(id) || pat2.test(id)) {
            return 1;
        } else {
            return 3;
        }
    }, TN_checkPassport: function (id) {
        var pat1, pat2;
        if (!id) {
            return 2;
        }
        pat1 = /^[a-zA-Z]{5,17}$/;
        pat2 = /^[a-zA-Z0-9]{5,17}$/;
        if (pat1.test(id) || pat2.test(id)) {
            return 1;
        } else {
            return 3;
        }
    }, TN_strlen: function (str) {
        var len = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charCodeAt(i) > 255 || str.charCodeAt(i) < 0) {
                len += 2;
            } else {
                len++;
            }
        }
        return len;
    }, TN_checkIdCard: function (idcard) {
        if (idcard === '') {
            return 2;
        }
        var Errors = [1, 3, 3, 3, 3];
        var area = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外"
        }
        var idcard, Y, JYM, JYM_X;
        var S, M, M_X;
        var idcard_array = [];
        idcard_array = idcard.split("");
        if (area[parseInt(idcard.substr(0, 2))] == null) {
            return Errors[4];
        }
        switch (idcard.length) {
            case 15:
                if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 400 == 0)) {
                    ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
                } else {
                    ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
                }
                if (ereg.test(idcard)) {
                    return 15;
                } else {
                    return Errors[2];
                }
                break;
            case 18:
                if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                    ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
                } else {
                    ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
                }
                if (ereg.test(idcard)) {
                    S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
                        + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
                        + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
                        + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
                        + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
                        + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
                        + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
                        + parseInt(idcard_array[7]) * 1
                        + parseInt(idcard_array[8]) * 6
                        + parseInt(idcard_array[9]) * 3;
                    Y = S % 11;
                    M = "F";
                    JYM = "10x98765432";
                    JYM_X = "10X98765432";
                    M = JYM.substr(Y, 1);
                    M_X = JYM_X.substr(Y, 1);
                    if (M == idcard_array[17] || M_X == idcard_array[17]) {
                        return 1;
                    } else {
                        return Errors[3];
                    }
                } else {
                    return Errors[2];
                }
                break;
            default:
                return Errors[1];
                break;
        }
    }
});
;//     Underscore.js 1.6.0
//     http://underscorejs.org
//     (c) 2009-2014 Jeremy Ashkenas, DocumentCloud and Investigative Reporters & Editors
//     Underscore may be freely distributed under the MIT license.
(function () {
    var n = this, t = n._, r = {}, e = Array.prototype, u = Object.prototype, i = Function.prototype, a = e.push,
        o = e.slice, c = e.concat, l = u.toString, f = u.hasOwnProperty, s = e.forEach, p = e.map, h = e.reduce,
        v = e.reduceRight, g = e.filter, d = e.every, m = e.some, y = e.indexOf, b = e.lastIndexOf, x = Array.isArray,
        w = Object.keys, _ = i.bind, j = function (n) {
            return n instanceof j ? n : this instanceof j ? void (this._wrapped = n) : new j(n)
        };
    "undefined" != typeof exports ? ("undefined" != typeof module && module.exports && (exports = module.exports = j), exports._ = j) : n._ = j, j.VERSION = "1.6.0";
    var A = j.each = j.forEach = function (n, t, e) {
        if (null == n) return n;
        if (s && n.forEach === s) n.forEach(t, e); else if (n.length === +n.length) {
            for (var u = 0, i = n.length; i > u; u++) if (t.call(e, n[u], u, n) === r) return
        } else for (var a = j.keys(n), u = 0, i = a.length; i > u; u++) if (t.call(e, n[a[u]], a[u], n) === r) return;
        return n
    };
    j.map = j.collect = function (n, t, r) {
        var e = [];
        return null == n ? e : p && n.map === p ? n.map(t, r) : (A(n, function (n, u, i) {
            e.push(t.call(r, n, u, i))
        }), e)
    };
    var O = "Reduce of empty array with no initial value";
    j.reduce = j.foldl = j.inject = function (n, t, r, e) {
        var u = arguments.length > 2;
        if (null == n && (n = []), h && n.reduce === h) return e && (t = j.bind(t, e)), u ? n.reduce(t, r) : n.reduce(t);
        if (A(n, function (n, i, a) {
            u ? r = t.call(e, r, n, i, a) : (r = n, u = !0)
        }), !u) throw new TypeError(O);
        return r
    }, j.reduceRight = j.foldr = function (n, t, r, e) {
        var u = arguments.length > 2;
        if (null == n && (n = []), v && n.reduceRight === v) return e && (t = j.bind(t, e)), u ? n.reduceRight(t, r) : n.reduceRight(t);
        var i = n.length;
        if (i !== +i) {
            var a = j.keys(n);
            i = a.length
        }
        if (A(n, function (o, c, l) {
            c = a ? a[--i] : --i, u ? r = t.call(e, r, n[c], c, l) : (r = n[c], u = !0)
        }), !u) throw new TypeError(O);
        return r
    }, j.find = j.detect = function (n, t, r) {
        var e;
        return k(n, function (n, u, i) {
            return t.call(r, n, u, i) ? (e = n, !0) : void 0
        }), e
    }, j.filter = j.select = function (n, t, r) {
        var e = [];
        return null == n ? e : g && n.filter === g ? n.filter(t, r) : (A(n, function (n, u, i) {
            t.call(r, n, u, i) && e.push(n)
        }), e)
    }, j.reject = function (n, t, r) {
        return j.filter(n, function (n, e, u) {
            return !t.call(r, n, e, u)
        }, r)
    }, j.every = j.all = function (n, t, e) {
        t || (t = j.identity);
        var u = !0;
        return null == n ? u : d && n.every === d ? n.every(t, e) : (A(n, function (n, i, a) {
            return (u = u && t.call(e, n, i, a)) ? void 0 : r
        }), !!u)
    };
    var k = j.some = j.any = function (n, t, e) {
        t || (t = j.identity);
        var u = !1;
        return null == n ? u : m && n.some === m ? n.some(t, e) : (A(n, function (n, i, a) {
            return u || (u = t.call(e, n, i, a)) ? r : void 0
        }), !!u)
    };
    j.contains = j.include = function (n, t) {
        return null == n ? !1 : y && n.indexOf === y ? n.indexOf(t) != -1 : k(n, function (n) {
            return n === t
        })
    }, j.invoke = function (n, t) {
        var r = o.call(arguments, 2), e = j.isFunction(t);
        return j.map(n, function (n) {
            return (e ? t : n[t]).apply(n, r)
        })
    }, j.pluck = function (n, t) {
        return j.map(n, j.property(t))
    }, j.where = function (n, t) {
        return j.filter(n, j.matches(t))
    }, j.findWhere = function (n, t) {
        return j.find(n, j.matches(t))
    }, j.max = function (n, t, r) {
        if (!t && j.isArray(n) && n[0] === +n[0] && n.length < 65535) return Math.max.apply(Math, n);
        var e = -1 / 0, u = -1 / 0;
        return A(n, function (n, i, a) {
            var o = t ? t.call(r, n, i, a) : n;
            o > u && (e = n, u = o)
        }), e
    }, j.min = function (n, t, r) {
        if (!t && j.isArray(n) && n[0] === +n[0] && n.length < 65535) return Math.min.apply(Math, n);
        var e = 1 / 0, u = 1 / 0;
        return A(n, function (n, i, a) {
            var o = t ? t.call(r, n, i, a) : n;
            u > o && (e = n, u = o)
        }), e
    }, j.shuffle = function (n) {
        var t, r = 0, e = [];
        return A(n, function (n) {
            t = j.random(r++), e[r - 1] = e[t], e[t] = n
        }), e
    }, j.sample = function (n, t, r) {
        return null == t || r ? (n.length !== +n.length && (n = j.values(n)), n[j.random(n.length - 1)]) : j.shuffle(n).slice(0, Math.max(0, t))
    };
    var E = function (n) {
        return null == n ? j.identity : j.isFunction(n) ? n : j.property(n)
    };
    j.sortBy = function (n, t, r) {
        return t = E(t), j.pluck(j.map(n, function (n, e, u) {
            return {value: n, index: e, criteria: t.call(r, n, e, u)}
        }).sort(function (n, t) {
            var r = n.criteria, e = t.criteria;
            if (r !== e) {
                if (r > e || r === void 0) return 1;
                if (e > r || e === void 0) return -1
            }
            return n.index - t.index
        }), "value")
    };
    var F = function (n) {
        return function (t, r, e) {
            var u = {};
            return r = E(r), A(t, function (i, a) {
                var o = r.call(e, i, a, t);
                n(u, o, i)
            }), u
        }
    };
    j.groupBy = F(function (n, t, r) {
        j.has(n, t) ? n[t].push(r) : n[t] = [r]
    }), j.indexBy = F(function (n, t, r) {
        n[t] = r
    }), j.countBy = F(function (n, t) {
        j.has(n, t) ? n[t]++ : n[t] = 1
    }), j.sortedIndex = function (n, t, r, e) {
        r = E(r);
        for (var u = r.call(e, t), i = 0, a = n.length; a > i;) {
            var o = i + a >>> 1;
            r.call(e, n[o]) < u ? i = o + 1 : a = o
        }
        return i
    }, j.toArray = function (n) {
        return n ? j.isArray(n) ? o.call(n) : n.length === +n.length ? j.map(n, j.identity) : j.values(n) : []
    }, j.size = function (n) {
        return null == n ? 0 : n.length === +n.length ? n.length : j.keys(n).length
    }, j.first = j.head = j.take = function (n, t, r) {
        return null == n ? void 0 : null == t || r ? n[0] : 0 > t ? [] : o.call(n, 0, t)
    }, j.initial = function (n, t, r) {
        return o.call(n, 0, n.length - (null == t || r ? 1 : t))
    }, j.last = function (n, t, r) {
        return null == n ? void 0 : null == t || r ? n[n.length - 1] : o.call(n, Math.max(n.length - t, 0))
    }, j.rest = j.tail = j.drop = function (n, t, r) {
        return o.call(n, null == t || r ? 1 : t)
    }, j.compact = function (n) {
        return j.filter(n, j.identity)
    };
    var M = function (n, t, r) {
        return t && j.every(n, j.isArray) ? c.apply(r, n) : (A(n, function (n) {
            j.isArray(n) || j.isArguments(n) ? t ? a.apply(r, n) : M(n, t, r) : r.push(n)
        }), r)
    };
    j.flatten = function (n, t) {
        return M(n, t, [])
    }, j.without = function (n) {
        return j.difference(n, o.call(arguments, 1))
    }, j.partition = function (n, t) {
        var r = [], e = [];
        return A(n, function (n) {
            (t(n) ? r : e).push(n)
        }), [r, e]
    }, j.uniq = j.unique = function (n, t, r, e) {
        j.isFunction(t) && (e = r, r = t, t = !1);
        var u = r ? j.map(n, r, e) : n, i = [], a = [];
        return A(u, function (r, e) {
            (t ? e && a[a.length - 1] === r : j.contains(a, r)) || (a.push(r), i.push(n[e]))
        }), i
    }, j.union = function () {
        return j.uniq(j.flatten(arguments, !0))
    }, j.intersection = function (n) {
        var t = o.call(arguments, 1);
        return j.filter(j.uniq(n), function (n) {
            return j.every(t, function (t) {
                return j.contains(t, n)
            })
        })
    }, j.difference = function (n) {
        var t = c.apply(e, o.call(arguments, 1));
        return j.filter(n, function (n) {
            return !j.contains(t, n)
        })
    }, j.zip = function () {
        for (var n = j.max(j.pluck(arguments, "length").concat(0)), t = new Array(n), r = 0; n > r; r++) t[r] = j.pluck(arguments, "" + r);
        return t
    }, j.object = function (n, t) {
        if (null == n) return {};
        for (var r = {}, e = 0, u = n.length; u > e; e++) t ? r[n[e]] = t[e] : r[n[e][0]] = n[e][1];
        return r
    }, j.indexOf = function (n, t, r) {
        if (null == n) return -1;
        var e = 0, u = n.length;
        if (r) {
            if ("number" != typeof r) return e = j.sortedIndex(n, t), n[e] === t ? e : -1;
            e = 0 > r ? Math.max(0, u + r) : r
        }
        if (y && n.indexOf === y) return n.indexOf(t, r);
        for (; u > e; e++) if (n[e] === t) return e;
        return -1
    }, j.lastIndexOf = function (n, t, r) {
        if (null == n) return -1;
        var e = null != r;
        if (b && n.lastIndexOf === b) return e ? n.lastIndexOf(t, r) : n.lastIndexOf(t);
        for (var u = e ? r : n.length; u--;) if (n[u] === t) return u;
        return -1
    }, j.range = function (n, t, r) {
        arguments.length <= 1 && (t = n || 0, n = 0), r = arguments[2] || 1;
        for (var e = Math.max(Math.ceil((t - n) / r), 0), u = 0, i = new Array(e); e > u;) i[u++] = n, n += r;
        return i
    };
    var R = function () {
    };
    j.bind = function (n, t) {
        var r, e;
        if (_ && n.bind === _) return _.apply(n, o.call(arguments, 1));
        if (!j.isFunction(n)) throw new TypeError;
        return r = o.call(arguments, 2), e = function () {
            if (!(this instanceof e)) return n.apply(t, r.concat(o.call(arguments)));
            R.prototype = n.prototype;
            var u = new R;
            R.prototype = null;
            var i = n.apply(u, r.concat(o.call(arguments)));
            return Object(i) === i ? i : u
        }
    }, j.partial = function (n) {
        var t = o.call(arguments, 1);
        return function () {
            for (var r = 0, e = t.slice(), u = 0, i = e.length; i > u; u++) e[u] === j && (e[u] = arguments[r++]);
            for (; r < arguments.length;) e.push(arguments[r++]);
            return n.apply(this, e)
        }
    }, j.bindAll = function (n) {
        var t = o.call(arguments, 1);
        if (0 === t.length) throw new Error("bindAll must be passed function names");
        return A(t, function (t) {
            n[t] = j.bind(n[t], n)
        }), n
    }, j.memoize = function (n, t) {
        var r = {};
        return t || (t = j.identity), function () {
            var e = t.apply(this, arguments);
            return j.has(r, e) ? r[e] : r[e] = n.apply(this, arguments)
        }
    }, j.delay = function (n, t) {
        var r = o.call(arguments, 2);
        return setTimeout(function () {
            return n.apply(null, r)
        }, t)
    }, j.defer = function (n) {
        return j.delay.apply(j, [n, 1].concat(o.call(arguments, 1)))
    }, j.throttle = function (n, t, r) {
        var e, u, i, a = null, o = 0;
        r || (r = {});
        var c = function () {
            o = r.leading === !1 ? 0 : j.now(), a = null, i = n.apply(e, u), e = u = null
        };
        return function () {
            var l = j.now();
            o || r.leading !== !1 || (o = l);
            var f = t - (l - o);
            return e = this, u = arguments, 0 >= f ? (clearTimeout(a), a = null, o = l, i = n.apply(e, u), e = u = null) : a || r.trailing === !1 || (a = setTimeout(c, f)), i
        }
    }, j.debounce = function (n, t, r) {
        var e, u, i, a, o, c = function () {
            var l = j.now() - a;
            t > l ? e = setTimeout(c, t - l) : (e = null, r || (o = n.apply(i, u), i = u = null))
        };
        return function () {
            i = this, u = arguments, a = j.now();
            var l = r && !e;
            return e || (e = setTimeout(c, t)), l && (o = n.apply(i, u), i = u = null), o
        }
    }, j.once = function (n) {
        var t, r = !1;
        return function () {
            return r ? t : (r = !0, t = n.apply(this, arguments), n = null, t)
        }
    }, j.wrap = function (n, t) {
        return j.partial(t, n)
    }, j.compose = function () {
        var n = arguments;
        return function () {
            for (var t = arguments, r = n.length - 1; r >= 0; r--) t = [n[r].apply(this, t)];
            return t[0]
        }
    }, j.after = function (n, t) {
        return function () {
            return --n < 1 ? t.apply(this, arguments) : void 0
        }
    }, j.keys = function (n) {
        if (!j.isObject(n)) return [];
        if (w) return w(n);
        var t = [];
        for (var r in n) j.has(n, r) && t.push(r);
        return t
    }, j.values = function (n) {
        for (var t = j.keys(n), r = t.length, e = new Array(r), u = 0; r > u; u++) e[u] = n[t[u]];
        return e
    }, j.pairs = function (n) {
        for (var t = j.keys(n), r = t.length, e = new Array(r), u = 0; r > u; u++) e[u] = [t[u], n[t[u]]];
        return e
    }, j.invert = function (n) {
        for (var t = {}, r = j.keys(n), e = 0, u = r.length; u > e; e++) t[n[r[e]]] = r[e];
        return t
    }, j.functions = j.methods = function (n) {
        var t = [];
        for (var r in n) j.isFunction(n[r]) && t.push(r);
        return t.sort()
    }, j.extend = function (n) {
        return A(o.call(arguments, 1), function (t) {
            if (t) for (var r in t) n[r] = t[r]
        }), n
    }, j.pick = function (n) {
        var t = {}, r = c.apply(e, o.call(arguments, 1));
        return A(r, function (r) {
            r in n && (t[r] = n[r])
        }), t
    }, j.omit = function (n) {
        var t = {}, r = c.apply(e, o.call(arguments, 1));
        for (var u in n) j.contains(r, u) || (t[u] = n[u]);
        return t
    }, j.defaults = function (n) {
        return A(o.call(arguments, 1), function (t) {
            if (t) for (var r in t) n[r] === void 0 && (n[r] = t[r])
        }), n
    }, j.clone = function (n) {
        return j.isObject(n) ? j.isArray(n) ? n.slice() : j.extend({}, n) : n
    }, j.tap = function (n, t) {
        return t(n), n
    };
    var S = function (n, t, r, e) {
        if (n === t) return 0 !== n || 1 / n == 1 / t;
        if (null == n || null == t) return n === t;
        n instanceof j && (n = n._wrapped), t instanceof j && (t = t._wrapped);
        var u = l.call(n);
        if (u != l.call(t)) return !1;
        switch (u) {
            case"[object String]":
                return n == String(t);
            case"[object Number]":
                return n != +n ? t != +t : 0 == n ? 1 / n == 1 / t : n == +t;
            case"[object Date]":
            case"[object Boolean]":
                return +n == +t;
            case"[object RegExp]":
                return n.source == t.source && n.global == t.global && n.multiline == t.multiline && n.ignoreCase == t.ignoreCase
        }
        if ("object" != typeof n || "object" != typeof t) return !1;
        for (var i = r.length; i--;) if (r[i] == n) return e[i] == t;
        var a = n.constructor, o = t.constructor;
        if (a !== o && !(j.isFunction(a) && a instanceof a && j.isFunction(o) && o instanceof o) && "constructor" in n && "constructor" in t) return !1;
        r.push(n), e.push(t);
        var c = 0, f = !0;
        if ("[object Array]" == u) {
            if (c = n.length, f = c == t.length) for (; c-- && (f = S(n[c], t[c], r, e));) ;
        } else {
            for (var s in n) if (j.has(n, s) && (c++, !(f = j.has(t, s) && S(n[s], t[s], r, e)))) break;
            if (f) {
                for (s in t) if (j.has(t, s) && !c--) break;
                f = !c
            }
        }
        return r.pop(), e.pop(), f
    };
    j.isEqual = function (n, t) {
        return S(n, t, [], [])
    }, j.isEmpty = function (n) {
        if (null == n) return !0;
        if (j.isArray(n) || j.isString(n)) return 0 === n.length;
        for (var t in n) if (j.has(n, t)) return !1;
        return !0
    }, j.isElement = function (n) {
        return !(!n || 1 !== n.nodeType)
    }, j.isArray = x || function (n) {
        return "[object Array]" == l.call(n)
    }, j.isObject = function (n) {
        return n === Object(n)
    }, A(["Arguments", "Function", "String", "Number", "Date", "RegExp"], function (n) {
        j["is" + n] = function (t) {
            return l.call(t) == "[object " + n + "]"
        }
    }), j.isArguments(arguments) || (j.isArguments = function (n) {
        return !(!n || !j.has(n, "callee"))
    }), "function" != typeof /./ && (j.isFunction = function (n) {
        return "function" == typeof n
    }), j.isFinite = function (n) {
        return isFinite(n) && !isNaN(parseFloat(n))
    }, j.isNaN = function (n) {
        return j.isNumber(n) && n != +n
    }, j.isBoolean = function (n) {
        return n === !0 || n === !1 || "[object Boolean]" == l.call(n)
    }, j.isNull = function (n) {
        return null === n
    }, j.isUndefined = function (n) {
        return n === void 0
    }, j.has = function (n, t) {
        return f.call(n, t)
    }, j.noConflict = function () {
        return n._ = t, this
    }, j.identity = function (n) {
        return n
    }, j.constant = function (n) {
        return function () {
            return n
        }
    }, j.property = function (n) {
        return function (t) {
            return t[n]
        }
    }, j.matches = function (n) {
        return function (t) {
            if (t === n) return !0;
            for (var r in n) if (n[r] !== t[r]) return !1;
            return !0
        }
    }, j.times = function (n, t, r) {
        for (var e = Array(Math.max(0, n)), u = 0; n > u; u++) e[u] = t.call(r, u);
        return e
    }, j.random = function (n, t) {
        return null == t && (t = n, n = 0), n + Math.floor(Math.random() * (t - n + 1))
    }, j.now = Date.now || function () {
        return (new Date).getTime()
    };
    var T = {escape: {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#x27;"}};
    T.unescape = j.invert(T.escape);
    var I = {
        escape: new RegExp("[" + j.keys(T.escape).join("") + "]", "g"),
        unescape: new RegExp("(" + j.keys(T.unescape).join("|") + ")", "g")
    };
    j.each(["escape", "unescape"], function (n) {
        j[n] = function (t) {
            return null == t ? "" : ("" + t).replace(I[n], function (t) {
                return T[n][t]
            })
        }
    }), j.result = function (n, t) {
        if (null == n) return void 0;
        var r = n[t];
        return j.isFunction(r) ? r.call(n) : r
    }, j.mixin = function (n) {
        A(j.functions(n), function (t) {
            var r = j[t] = n[t];
            j.prototype[t] = function () {
                var n = [this._wrapped];
                return a.apply(n, arguments), z.call(this, r.apply(j, n))
            }
        })
    };
    var N = 0;
    j.uniqueId = function (n) {
        var t = ++N + "";
        return n ? n + t : t
    }, j.templateSettings = {evaluate: /<%([\s\S]+?)%>/g, interpolate: /<%=([\s\S]+?)%>/g, escape: /<%-([\s\S]+?)%>/g};
    var q = /(.)^/, B = {"'": "'", "\\": "\\", "\r": "r", "\n": "n", "	": "t", "\u2028": "u2028", "\u2029": "u2029"},
        D = /\\|'|\r|\n|\t|\u2028|\u2029/g;
    j.template = function (n, t, r) {
        var e;
        r = j.defaults({}, r, j.templateSettings);
        var u = new RegExp([(r.escape || q).source, (r.interpolate || q).source, (r.evaluate || q).source].join("|") + "|$", "g"),
            i = 0, a = "__p+='";
        n.replace(u, function (t, r, e, u, o) {
            return a += n.slice(i, o).replace(D, function (n) {
                return "\\" + B[n]
            }), r && (a += "'+\n((__t=(" + r + "))==null?'':_.escape(__t))+\n'"), e && (a += "'+\n((__t=(" + e + "))==null?'':__t)+\n'"), u && (a += "';\n" + u + "\n__p+='"), i = o + t.length, t
        }), a += "';\n", r.variable || (a = "with(obj||{}){\n" + a + "}\n"), a = "var __t,__p='',__j=Array.prototype.join," + "print=function(){__p+=__j.call(arguments,'');};\n" + a + "return __p;\n";
        try {
            e = new Function(r.variable || "obj", "_", a)
        } catch (o) {
            throw o.source = a, o
        }
        if (t) return e(t, j);
        var c = function (n) {
            return e.call(this, n, j)
        };
        return c.source = "function(" + (r.variable || "obj") + "){\n" + a + "}", c
    }, j.chain = function (n) {
        return j(n).chain()
    };
    var z = function (n) {
        return this._chain ? j(n).chain() : n
    };
    j.mixin(j), A(["pop", "push", "reverse", "shift", "sort", "splice", "unshift"], function (n) {
        var t = e[n];
        j.prototype[n] = function () {
            var r = this._wrapped;
            return t.apply(r, arguments), "shift" != n && "splice" != n || 0 !== r.length || delete r[0], z.call(this, r)
        }
    }), A(["concat", "join", "slice"], function (n) {
        var t = e[n];
        j.prototype[n] = function () {
            return z.call(this, t.apply(this._wrapped, arguments))
        }
    }), j.extend(j.prototype, {
        chain: function () {
            return this._chain = !0, this
        }, value: function () {
            return this._wrapped
        }
    }), "function" == typeof define && define.amd && define("underscore", [], function () {
        return j
    })
}).call(this);
;
;(function (win, _) {
    var array = [];
    var push = array.push;
    var slice = array.slice;
    var splice = array.splice;
    var Events = {
        on: function (name, callback, context) {
            if (!eventsApi(this, 'on', name, [callback, context]) || !callback) return this;
            this._events || (this._events = {});
            var events = this._events[name] || (this._events[name] = []);
            events.push({callback: callback, context: context, ctx: context || this});
            return this;
        }, once: function (name, callback, context) {
            if (!eventsApi(this, 'once', name, [callback, context]) || !callback) return this;
            var self = this;
            var once = _.once(function () {
                self.off(name, once);
                callback.apply(this, arguments);
            });
            once._callback = callback;
            return this.on(name, once, context);
        }, off: function (name, callback, context) {
            var retain, ev, events, names, i, l, j, k;
            if (!this._events || !eventsApi(this, 'off', name, [callback, context])) return this;
            if (!name && !callback && !context) {
                this._events = void 0;
                return this;
            }
            names = name ? [name] : _.keys(this._events);
            for (i = 0, l = names.length; i < l; i++) {
                name = names[i];
                if (events = this._events[name]) {
                    this._events[name] = retain = [];
                    if (callback || context) {
                        for (j = 0, k = events.length; j < k; j++) {
                            ev = events[j];
                            if ((callback && callback !== ev.callback && callback !== ev.callback._callback) || (context && context !== ev.context)) {
                                retain.push(ev);
                            }
                        }
                    }
                    if (!retain.length) delete this._events[name];
                }
            }
            return this;
        }, trigger: function (name) {
            if (!this._events) return this;
            var args = slice.call(arguments, 1);
            if (!eventsApi(this, 'trigger', name, args)) return this;
            var events = this._events[name];
            var allEvents = this._events.all;
            if (events) triggerEvents(events, args);
            if (allEvents) triggerEvents(allEvents, arguments);
            return this;
        }, stopListening: function (obj, name, callback) {
            var listeningTo = this._listeningTo;
            if (!listeningTo) return this;
            var remove = !name && !callback;
            if (!callback && typeof name === 'object') callback = this;
            if (obj) (listeningTo = {})[obj._listenId] = obj;
            for (var id in listeningTo) {
                obj = listeningTo[id];
                obj.off(name, callback, this);
                if (remove || _.isEmpty(obj._events)) delete this._listeningTo[id];
            }
            return this;
        }
    };
    var eventSplitter = /\s+/;
    var eventsApi = function (obj, action, name, rest) {
        if (!name) return true;
        if (typeof name === 'object') {
            for (var key in name) {
                obj[action].apply(obj, [key, name[key]].concat(rest));
            }
            return false;
        }
        if (eventSplitter.test(name)) {
            var names = name.split(eventSplitter);
            for (var i = 0, l = names.length; i < l; i++) {
                obj[action].apply(obj, [names[i]].concat(rest));
            }
            return false;
        }
        return true;
    };
    var triggerEvents = function (events, args) {
        var ev, i = -1, l = events.length, a1 = args[0], a2 = args[1], a3 = args[2];
        switch (args.length) {
            case 0:
                while (++i < l) (ev = events[i]).callback.call(ev.ctx);
                return;
            case 1:
                while (++i < l) (ev = events[i]).callback.call(ev.ctx, a1);
                return;
            case 2:
                while (++i < l) (ev = events[i]).callback.call(ev.ctx, a1, a2);
                return;
            case 3:
                while (++i < l) (ev = events[i]).callback.call(ev.ctx, a1, a2, a3);
                return;
            default:
                while (++i < l) (ev = events[i]).callback.apply(ev.ctx, args);
                return;
        }
    };
    var listenMethods = {listenTo: 'on', listenToOnce: 'once'};
    _.each(listenMethods, function (implementation, method) {
        Events[method] = function (obj, name, callback) {
            var listeningTo = this._listeningTo || (this._listeningTo = {});
            var id = obj._listenId || (obj._listenId = _.uniqueId('l'));
            listeningTo[id] = obj;
            if (!callback && typeof name === 'object') callback = this;
            obj[implementation](name, callback, this);
            return this;
        };
    });
    Events.bind = Events.on;
    Events.unbind = Events.off;

    function Event() {
    };Event.prototype = Events;
    win.Event = Event;
    win.Events = Events;
})(window, _);
;
;(function () {
    var isLogin = window.isLogin || false;
    var checkPass = true;
    var deliveryFee_ = parseInt($('#sendFee').val());
    var detailAddress_ = '';
    var descriptionText_ = '';
    var accecptChecked = false;
    var isPaperTicket = false;
    var _isBind12306Count = false;
    var identityVerificationContinue = true;
    var identityVerificationAble = true;
    var commonProto = $.extend({
        addError: function (type, errorType) {
            var self = this;
            var items = self.items;
            var item = items[type];
            var msg = self.errorMsg[type][errorType];
            var errorEle;
            if (!item) {
                return;
            }
            if (!item.error) {
                if (item.errorEle) {
                    item.errorEle.remove();
                }
                item.error = true;
                item.wrap.addClass('err');
                errorEle = self.errorTip.clone();
                if (type == 'idCode' && errorType == '5') {
                    errorEle.find('.error_icon').removeClass('error_icon').addClass('info_icon');
                    item.wrap.removeClass('err');
                    item.error = false;
                }
                errorEle.find('.error_con').html(msg);
                if (type == 'idCode' && errorType == '6') {
                    errorEle.find('.error_con').append($('<span class="btn_bind12306">马上绑定</span>'));
                }
                errorEle.insertAfter(item.input);
                item.errorEle = errorEle;
                item.errorType = errorType;
            } else if (item.errorType !== errorType) {
                item.errorEle.find('.error_con').html(msg);
                item.errorType = errorType;
            }
        }, removeError: function (type, errorType, flag) {
            var self = this;
            var items = self.items;
            var item = items[type];
            if (errorType) {
                if (item.error && item && item.errorType == errorType) {
                    item.wrap.removeClass('err');
                    item.errorEle.remove();
                    item.error = false;
                    item.errorType = 0;
                }
            } else {
                if (item.error && item) {
                    item.wrap.removeClass('err');
                    item.errorEle.remove();
                    item.error = false;
                    item.errorType = 0;
                }
            }
            if (flag) {
                if (item && item.errorEle) {
                    item.errorEle.remove();
                    item.error = false;
                    item.errorType = 0;
                }
            }
        }, hideDelete: function () {
            this.ishideDelete = true;
            this.deleteBtn.hide();
        }, showDelete: function () {
            this.ishideDelete = false;
        }
    }, Events);

    function DateSelector(yearEle, monthEle, dateEle, date) {
        this.yearEle = yearEle;
        this.monthEle = monthEle;
        this.dateEle = dateEle;
        this.year = 0;
        this.month = 0;
        this.date = 0;
        this.date = date;
        this._maxDays = 0;
        this.init();
    }

    $.extend(DateSelector.prototype, {
        init: function () {
            var self = this;
            var yearEle = this.yearEle;
            var monthEle = this.monthEle;
            var dateEle = this.dateEle;
            yearEle.change(function () {
                self.checkMaxDays();
                self.trigger('change');
            });
            monthEle.change(function () {
                self.checkMaxDays();
                self.trigger('change');
            });
            dateEle.change(function () {
                self.trigger('change');
            });
            this.buildYear();
            this.buildMonth();
            this.checkMaxDays();
        }, buildYear: function () {
            var yearEle = this.yearEle;
            var baseYear = 1900;
            var html = '';
            html += '<option value="0">请选择</option>';
            for (var i = 115; i >= 0; i--) {
                html += '<option value="' + (baseYear + i) + '">' + (baseYear + i) + '</option>'
            }
            yearEle.html(html);
        }, buildMonth: function () {
            var monthEle = this.monthEle;
            var html = '';
            html += '<option value="0">请选择</option>';
            for (var i = 1; i <= 12; i++) {
                html += '<option value="' + i + '">' + i + '</option>'
            }
            monthEle.html(html);
        }, buildDate: function (max) {
            var dateEle = this.dateEle;
            var html = '';
            max = max || 0;
            html += '<option value="0">请选择</option>';
            for (var i = 1; i <= max; i++) {
                html += '<option value="' + i + '">' + i + '</option>'
            }
            dateEle.html(html);
        }, setYear: function (year, callback) {
            this.yearEle.val(year);
            callback && callback();
        }, setMonth: function (month, callback) {
            this.monthEle.val(month);
            this.checkMaxDays();
            callback && callback();
        }, setDate: function (date) {
            this.dateEle.val(date);
        }, checkMaxDays: function () {
            var year, month, date, days;
            year = this.yearEle.val();
            month = this.monthEle.val();
            if (year != '0' && month != '0') {
                date = new Date(year, month, 0);
                days = date.getDate();
                if (this._maxDays != days) {
                    this.buildDate(days);
                    this._maxDays = days;
                }
            } else {
                this.buildDate(0);
            }
        }, set: function (date) {
            var self = this;
            date = date.split('-');
            this.setYear(date[0], function () {
                self.setMonth(parseInt(date[1]), function () {
                    self.setDate(parseInt(date[2]));
                });
            });
        }, get: function () {
            var year = this.yearEle.val();
            var month = this.monthEle.val();
            var date = this.dateEle.val();
            if (year == 0 || month == 0 || date == 0) {
                return false;
            }
            return year + '-' + month + '-' + date;
        }
    }, Events);

    function Ticket(data) {
        this.wrap = $('#J_Ticket');
        this.loading = false;
        this.seatLoading = '正在查询...';
        this.seatAvailable = '<i class="icon icon_right"></i>可预订';
        this.seaUnavailable = '<i class="icon icon_wrong"></i>不可预订';
        this.type = 0;
        this.price = 0;
        this.data = data || {};
        this.init();
    }

    $.extend(Ticket.prototype, {
        init: function () {
            var self = this;
            var wrap = self.wrap;
            var hiddenInputs = wrap.find(':hidden');
            var changeSeatType = wrap.find('.J_TrainType');
            var trainTip = wrap.find('.J_TrainSeatTip');
            var standingTicket = wrap.find('#J_AcceptStandingTicket');
            var J_SleepTicketTip = wrap.find('#J_SleepTicketTip');
            var ticketInfo = {};
            hiddenInputs.each(function () {
                ticketInfo[this.name] = this.value;
            });
            changeSeatType.click(function () {
                var type = $(this).attr("data_resId");
                if (self.data.resId != type) {
                    $(this).siblings('.J_TrainType').removeClass("selected");
                    $(this).addClass("selected");
                    if (type == 1 || type == 2) {
                        layer.tips(seatTypeTipTemplate, this, {guide: 1, isGuide: true});
                    } else {
                        layer.closeTips();
                    }
                    self.data.resId = type;
                    self.type = type;
                    self.price = self.getPrice(type);
                    self.seat = self.getSeat(type);
                    self.leftNumber = self.getLeftNumber(type);
                    standingTicket.find("span").text(self.getSeatName(type));
                    if (self.seat == 3 || self.seat == 8) {
                        standingTicket.show();
                        standingTicket.find("input[type='checkbox']").removeAttr("checked");
                    } else if (self.seat == 9) {
                        standingTicket.hide();
                        standingTicket.find("input[type='checkbox']").attr("checked", "checked");
                    } else {
                        standingTicket.hide();
                        standingTicket.find("input[type='checkbox']").removeAttr("checked");
                    }
                    if (self.seat == 5 || self.seat == 6) {
                        J_SleepTicketTip.show();
                    } else {
                        J_SleepTicketTip.hide();
                    }
                    self.trigger('change', type);
                }
            });
            self.getDefaultTicket();
            self.trainTip = trainTip;
            self.ticketInfo = ticketInfo;
        }, getDefaultTicket: function () {
            var self = this;
            var type = self.wrap.find(".selected").attr("data_resId");
            self.data.resId = type;
            self.type = type;
            self.price = self.getPrice(type);
            self.seat = self.getSeat(type);
        }, getSeatInfo: function (type) {
            var self = this;
            self.loading = true;
            setTimeout(function () {
                self.loading = false;
                self.trainTip.html(self.seatAvailable);
            }, 1000);
        }, getPrice: function (type) {
            var prices = this.data.prices;
            if (prices) {
                for (var i = 0; i < prices.length; i++) {
                    if (prices[i]['resId'] == type) {
                        return prices[i]['price'];
                    }
                }
            }
            return 0;
        }, getSeat: function (type) {
            var prices = this.data.prices;
            type = type || this.type;
            if (prices) {
                for (var i = 0; i < prices.length; i++) {
                    if (prices[i]['resId'] == type) {
                        return prices[i]['seat'];
                    }
                }
            }
            return '';
        }, getSeatName: function (type) {
            var prices = this.data.prices;
            type = type || this.type;
            if (prices) {
                for (var i = 0; i < prices.length; i++) {
                    if (prices[i]['resId'] == type) {
                        return prices[i]['seatName'];
                    }
                }
            }
            return '';
        }, getLeftNumber: function (type) {
            var prices = this.data.prices;
            type = type || this.type;
            if (prices) {
                for (var i = 0; i < prices.length; i++) {
                    if (prices[i]['resId'] == type) {
                        return prices[i]['leftNumber'];
                    }
                }
            }
            return '';
        }, get: function () {
            return this.data;
        }
    }, Events);

    function SendTicket(order) {
        this.order = order;
        this.container = $('#J_Send_content');
        this.price = 0;
        this.inDeliveryFlagTime = this.inDeliveryFlagTime();
        this.errorMsg = {
            username: {2: '请填写联系人姓名'},
            mobile: {'2': '请填写手机号', '3': '手机号码错误',},
            region: {2: '抱歉，您选择的地址无法保证在发车前送达，请更换地址 或选择 <span style="color: blue;">自取票</span>'},
            regionSelect: {2: '请选择省市区'},
            detailAddress: {2: '请填写详细地址'}
        };
        this.errorTip = $(errorTemplate);
        this.isValiding = false;
        this.init();
    }

    $.extend(SendTicket.prototype, commonProto, {
        init: function () {
            var self = this;
            var container = self.container;
            var username = container.find('[name="username"]');
            var mobile = container.find('[name="mobile"]');
            var usernameItem = username.parent();
            var mobileItem = mobile.parent();
            var detailAddress = container.find('[name="detailAddr"]');
            var detailAddressItem = detailAddress.parent();
            var selectProvince = container.find('#province_send');
            var selectCity = container.find('#city_send');
            var selectArea = container.find('#area_send');
            username.blur(function () {
                var val = this.value;
                var res = self.checkUsername(val);
                if (res === 1) {
                    self.removeError('username');
                } else {
                    self.addError('username', res);
                }
            });
            mobile.blur(function () {
                var val = this.value;
                var res = self.checkMobile(val);
                if (res === 1) {
                    self.removeError('mobile');
                } else {
                    self.addError('mobile', res);
                }
                if (0 == selectProvince.val() || 0 == selectCity.val() || 0 == selectArea.val()) {
                    checkPass = false;
                    $('.notSupportDispatch').css('display', 'none');
                    $('.selectRegion').css('display', 'inline');
                }
            });
            detailAddress.blur(function () {
                var val = this.value;
                var res = self.checkDetailAddress(val);
                if (res === 1) {
                    self.removeError('detailAddress');
                } else {
                    self.addError('detailAddress', res);
                }
            });
            if (self.inDeliveryFlagTime && (1 == selectCity.find("option:selected").attr('delivery-flag'))) {
                self.getStationHtml();
            } else {
                self.removeStationHtml();
            }
            selectProvince.change(function () {
                var addressCode = selectProvince.find("option:selected").attr('addr-id');
                var url = '/tn?r=train/TrainTicket/GetAddrList';
                var param = {'type': 2, 'addressCode': addressCode};
                accecptChecked = false;
                $.get(url, param, function (json) {
                    if (json.errorCode == 200) {
                        var cityList = json.data.cityList;
                        var cityHtml = "<option value='0' addr-id='0'>请选择市</option>";
                        for (var i = 0; i < cityList.length; i++) {
                            cityHtml += "<option value='" + cityList[i].addressName + "' addr-id='" + cityList[i].addressCode + "' delivery-flag='" + cityList[i].deliveryFlag + "'>" + cityList[i].addressName + "</option>";
                        }
                        selectCity.html(cityHtml);
                        $('.selectRegion').css('display', 'inline');
                        $('.notSupportDispatch').css('display', 'none');
                        if (2 == self.order.deliveryType) {
                            self.order.deliveryType = 1;
                            self.removeStationHtml();
                            self.detailAddressChange();
                            self.getConfigStationOther();
                        }
                        selectArea.html("<option value='0' addr-id='0'>请选择区/县</option>");
                    }
                }, 'json');
            });
            selectCity.change(function () {
                accecptChecked = false;
                var url = '/tn?r=train/TrainTicket/getDeliveryPriceByRegion';
                var param = {};
                param.departDate = window.ticketData.startDate;
                param.provinceId = $('#province_send option:selected').attr('addr-id');
                param.provinceName = $('#province_send option:selected').text();
                param.cityId = $('#city_send option:selected').attr('addr-id');
                param.cityName = $('#city_send option:selected').text();
                $.get(url, param, function (res) {
                    if (res.success && res.data && 1 == res.data.ifCanDelivery) {
                        var defaultFee = $('#sendFee').val();
                        if (0 != res.data.deliveryFee) defaultFee = res.data.deliveryFee;
                        deliveryFee_ = defaultFee;
                        $('#dispatchFeeDiv').css('display', 'block');
                        $('#deliveryFee').html('￥' + defaultFee);
                        var deliveryFlag = selectCity.find("option:selected").attr('delivery-flag');
                        if (self.inDeliveryFlagTime && (1 == deliveryFlag)) {
                            self.getStationHtml();
                        } else {
                            self.removeStationHtml();
                        }
                        self.getAreaHtml(0);
                        checkPass = true;
                        var detailAddr = $('input[name="detailAddr"]');
                        detailAddr.val('');
                        detailAddr.removeAttr('disabled');
                        self.order.calcPrice();
                    } else {
                        if (0 == param.cityId) {
                            $('.selectRegion').css('display', 'inline');
                            $('.notSupportDispatch').css('display', 'none');
                            selectArea.val(0);
                        } else {
                            $('.selectRegion').css('display', 'none');
                            $('.notSupportDispatch').css('display', 'inline');
                        }
                        self.removeStationHtml();
                        var detailAddr = $('input[name="detailAddr"]');
                        detailAddr.val('');
                        detailAddr.removeAttr('disabled');
                        selectArea.html("<option value='0' addr-id='0'>请选择区/县</option>");
                        checkPass = false;
                    }
                }, 'json');
            });
            selectArea.change(function () {
                var curId = $('#area_send option:selected').attr('addr-id');
                detailAddress_ = $('#description-text-' + curId).data('address') || "";
                descriptionText_ = $('#description-text-' + curId).html() || "";
                self.detailAddressChange();
                if (0 == selectArea.val()) {
                    $('.selectRegion').css('display', 'inline');
                    $('.notSupportDispatch').css('display', 'none');
                } else {
                    $('.selectRegion').css('display', 'none');
                    $('.notSupportDispatch').css('display', 'none');
                }
            });
            self.items = {
                username: {wrap: usernameItem, input: username},
                mobile: {wrap: mobileItem, input: mobile},
                detailAddress: {wrap: detailAddressItem, input: detailAddress}
            }
        }, get: function () {
            var self = this;
            if (self.order.needSend == true) {
                var res = {};
                var items = this.items;
                var container = this.container;
                var flag = true;
                var provinceName = container.find("select[name='province_invoice']").val();
                var cityName = container.find("select[name='city_invoice']").val();
                var area_invoice = container.find("select[name='area_invoice']").val();
                var detail_address = container.find('[name="detailAddr"]').val();
                var deliveryEndAddress = area_invoice + '#' + detail_address;
                var type, item, _type;
                for (type in items) {
                    item = items[type];
                    _type = type;
                    if (type == 'mobile') {
                        _type = 'telNum';
                    } else if (type == 'username') {
                        _type = 'receiverName';
                    }
                    if (item.error === void (0)) {
                        item.input.trigger('blur');
                        if (item.error === true) {
                            flag = false;
                        } else {
                            if (type != 'detailAddress') {
                                res[_type] = $.trim(item.input.val());
                            }
                        }
                    } else if (item.error === true) {
                        flag = false;
                    } else {
                        if (type != 'detailAddress') {
                            res[_type] = $.trim(item.input.val());
                        }
                    }
                }
                if (flag === false) {
                    return false;
                } else {
                    res.deliveryType = 1;
                    res.provinceName = provinceName;
                    res.cityName = cityName;
                    res.deliveryEndAddress = deliveryEndAddress;
                    return res;
                }
            } else {
                return false;
            }
        }, clear: function () {
            var self = this;
            var container = self.container;
            container.find('[name="username"]').val('');
            container.find('[name="mobile"]').val('');
            container.find('[name="detailAddr"]').val('');
            container.find('[name="postCode"]').val('');
            self.removeError('username');
            self.removeError('mobile');
            self.removeError('detailAddress');
        }, checkMobile: function (val) {
            val = $.trim(val);
            return $.TN_checkTel(val);
        }, checkUsername: function (val) {
            val = $.trim(val);
            return val === '' ? 2 : 1;
        }, checkDetailAddress: function (val) {
            val = $.trim(val);
            return val === '' ? 2 : 1;
        }, checkPostCode: function (val) {
            val = $.trim(val);
            return $.TN_checkPostcode(val)
        }, hideMobileLoginTip: function () {
            this.removeError('mobile');
        }, setMobile: function (tel) {
            var mobile = this.container.find('input[name="mobile"]');
            mobile.val(tel);
        }, getAreaHtml: function (deliveryFlag) {
            var self = this;
            var selectCity = this.container.find('#city_send');
            var selectArea = this.container.find('#area_send');
            var addressCode = selectCity.find("option:selected").attr('addr-id');
            var url = '/tn?r=train/TrainTicket/GetAddrList';
            var param = {'type': 3, 'addressCode': addressCode, 'deliveryFlag': deliveryFlag};
            $.get(url, param, function (json) {
                if (json.errorCode == 200) {
                    var areaList = json.data.areaList;
                    var areaHtml = "";
                    if (0 == deliveryFlag) {
                        areaHtml = "<option value='0' addr-id='0'>请选择区/县</option>";
                    }
                    var hideDispatchInfo = '';
                    var firstCode = 0;
                    $('#hideDispatchInfo').html('');
                    for (var j = 0; j < areaList.length; j++) {
                        if (0 == j) firstCode = areaList[j].addressCode;
                        areaHtml += "<option value='" + areaList[j].addressName + "' addr-id='" + areaList[j].addressCode + "'>" + areaList[j].addressName + "</option>";
                        var id_ = 'description-text-' + areaList[j].addressCode;
                        hideDispatchInfo = '<div id="' + id_ + '" data-address="' + areaList[j].detailAddressPC + '" style="display: none;"></div>';
                        $('#hideDispatchInfo').append(hideDispatchInfo);
                        $('#description-text-' + areaList[j].addressCode).html(areaList[j].descriptionTextPC);
                    }
                    selectArea.html(areaHtml);
                    self.detailAddressChange();
                }
            }, 'json');
        }, detailAddressChange: function () {
            var self = this;
            var selectArea = this.container.find('#area_send');
            var detailAddr = this.container.find('[name="detailAddr"]');
            if (2 == self.order.deliveryType) {
                var selectAreaName = selectArea.find("option:selected").val();
                detailAddr.val(selectAreaName + '悦途贵宾厅内（天津站二层候车大厅10-11检票口前）');
                self.removeError('detailAddress');
                detailAddr.attr('disabled', 'true');
            } else {
                detailAddr.val('');
                detailAddr.removeAttr('disabled');
            }
        }, getStationHtml: function () {
            var self = this;
            var checked = '';
            if (accecptChecked) checked = 'checked="checked"';
            var stationHtml = $('#T_StationDelivery').html();
            $('#J_StationDelivery').html(stationHtml);
            var acceptStation = $("input[name='acceptStation']");
            acceptStation.click(function () {
                if ($(this)[0].checked) {
                    self.getConfigStation();
                    self.getAreaHtml(1);
                    $('.station_tip').show();
                    $('.selectRegion').css('display', 'none');
                    $('.notSupportDispatch').css('display', 'none');
                    checkPass = true;
                    accecptChecked = true;
                } else {
                    self.getConfigStationOther();
                    self.getAreaHtml(0);
                    $('.station_tip').hide();
                    accecptChecked = false;
                }
            });
            $('.station_text').hover(function () {
                $('.station_tip').show();
            }, function () {
                if (false == acceptStation[0].checked) {
                    $('.station_tip').hide();
                }
            });
            $('.station_tip .close').click(function () {
                $('.station_tip').hide();
            });
        }, removeStationHtml: function () {
            $('#J_StationDelivery').html('');
        }, getConfigStation: function () {
            var self = this;
            self.order.sendFee = parseInt($("#yautuDeliverFee").val());
            self.order.purchasingFee = parseInt($('#yautuPurchaseFee').val());
            self.order.deliveryType = 2;
            $('#deliveryFee').html('￥' + self.order.sendFee);
            self.order.calcPrice();
        }, getConfigStationOther: function () {
            var self = this;
            self.order.sendFee = parseInt($("#sendFee").val());
            self.order.purchasingFee = parseInt($('#purchasingFee').val());
            self.order.deliveryType = 1;
            $('#deliveryFee').html('￥' + self.order.sendFee);
            self.order.calcPrice();
        }, inDeliveryFlagTime: function () {
            var yautuDeliverTimeRange = $('#yautuDeliverTimeRange').val();
            if (!yautuDeliverTimeRange == '') {
                var timeArr = yautuDeliverTimeRange.split('-');
                var yautuDeliverTimeBegin = timeArr[0];
                var yautuDeliverTimeEnd = timeArr[1];
                var now = new Date();
                var nowFormat = now.getFullYear() + '/' + (now.getMonth() + 1) + '/' + now.getDate();
                var departTime = new Date(nowFormat + ' ' + window.ticketData.departDepartTime + ':00').getTime();
                var begin = new Date(nowFormat + ' ' + yautuDeliverTimeBegin + ':00').getTime();
                var end = new Date(nowFormat + ' ' + yautuDeliverTimeEnd + ':00').getTime();
                if (departTime > begin && departTime < end) {
                    return true;
                } else {
                    return false
                }
            } else {
                return false;
            }
        }
    });

    function Contact() {
        this.container = $('#J_Contact_content');
        this.errorMsg = {
            username: {2: '请填写联系人姓名'},
            mobile: {
                '2': '请填写手机号',
                '3': '手机号码错误',
                '4': '您已是途牛会员，<a href="javascript:;">立刻登录</a>!'
            },
            email: {'2': '邮箱为空', '3': '邮箱格式错误'}
        }
        this.errorTip = $(errorTemplate);
        this.isValiding = false;
        this.isRegisted = false;
        this.init();
    }

    $.extend(Contact.prototype, commonProto, {
        init: function () {
            var self = this;
            var container = self.container;
            var username = container.find('[name="username"]');
            var mobile = container.find('[name="mobile"]');
            var largeMobile = container.find('.J_LargeMobile');
            var J_TipMobile = container.find(".J_TipMobile");
            var J_TipMobileC = container.find(".J_TipMobileC");
            var email = container.find('[name="email"]');
            var usernameItem = username.parent();
            var mobileItem = mobile.parent();
            var emailItem = email.parent();
            username.blur(function () {
                var val = this.value;
                var res = self.checkUsername(val);
                if (res === 1) {
                    self.removeError('username');
                } else {
                }
            });
            mobile.blur(function () {
                var val = this.value;
                largeMobile.hide();
                var res = self.checkMobile(val);
                if (res === 1) {
                    J_TipMobile.hide();
                    self.removeError('mobile');
                    if (!isLogin) {
                        self.checkMobileRes(val);
                    } else {
                        self.hideMobileLoginTip();
                    }
                } else {
                    J_TipMobile.hide();
                    self.addError('mobile', res);
                }
            });
            mobile.keyup(function () {
                var val = $.trim(this.value);
                if (val) {
                    val = val.replace(/(\d{4})/g, '$1 ').replace(/\s*$/, '');
                    largeMobile.html(val);
                    largeMobile.show();
                }
            });
            mobile.keydown(function () {
                largeMobile.hide();
            });
            email.blur(function () {
                var val = $.trim(this.value);
                if (!val) {
                    self.removeError('email');
                    return;
                }
                var res = self.checkEmail(val);
                if (res === 1) {
                    self.removeError('email');
                } else {
                    self.addError('email', res);
                }
            });
            J_TipMobile.hover(function () {
                J_TipMobileC.show();
            }, function () {
                J_TipMobileC.hide();
            });
            self.items = {
                username: {wrap: usernameItem, input: username},
                mobile: {wrap: mobileItem, input: mobile},
                email: {wrap: emailItem, input: email}
            }
        }, get: function () {
            var res = {};
            var items = this.items;
            var flag = true;
            var type, item, _type;
            for (type in items) {
                item = items[type];
                _type = type;
                if (type == 'mobile') {
                    _type = 'tel';
                } else if (type == 'username') {
                    _type = 'name';
                }
                if (item.error === void (0)) {
                    item.input.trigger('blur');
                    if (item.error === true) {
                        flag = false;
                    } else {
                        res[_type] = $.trim(item.input.val());
                    }
                } else if (item.error === true) {
                    flag = false;
                } else {
                    res[_type] = $.trim(item.input.val());
                }
            }
            if (flag === false) {
                return false;
            } else {
                return res;
            }
        }, set: function (data) {
            var items = this.items;
            items.username.input.val(data.name).trigger('blur');
            items.mobile.input.val(data.tel).trigger('blur');
            items.email.input.val(data.email).trigger('blur');
        }, clear: function () {
            var self = this;
            var container = self.container;
            var mobile = container.find('[name="mobile"]');
            var J_TipMobile = container.find(".J_TipMobile");
            mobile.val('');
            self.removeError('mobile');
            J_TipMobile.show();
        }, checkMobile: function (val) {
            val = $.trim(val);
            return $.TN_checkTel(val);
        }, checkEmail: function (val) {
            val = $.trim(val);
            return $.TN_checkEmail(val);
        }, checkUsername: function (val) {
            val = $.trim(val);
            return val === '' ? 2 : 1;
        }, checkMobileRes: function (val) {
            var self = this;
            var url = "/main.php?do=order_login_ajax&flag=check_login_tel&tel=" + val + "&catch=" + Math.random();
            var mobileEle = self.items.mobile.input;
            if (mobileEle._historyValue == val) {
                return;
            }
            mobileEle._historyValue = val;
            self.isValiding = true;
            $.ajax({
                url: url, type: 'post', dataType: 'text', success: function (data) {
                    self.isValiding = false;
                    if (data == 1) {
                        self.addRegTip();
                        self.isRegisted = true;
                    } else if (data == 2) {
                        self.isRegisted = true;
                    } else {
                        self.isRegisted = false;
                    }
                }
            });
        }, addRegTip: function () {
            var self = this;
            var mobile = self.items.mobile;
            self.addError('mobile', 4);
            var errorEle = mobile.errorEle;
            errorEle.find('a').click(function () {
                self.trigger('login');
            });
            self.loginTip = errorEle;
        }, hideMobileLoginTip: function () {
            this.removeError('mobile');
        }
    });

    function Passenger(data, order) {
        this._type = 'adult';
        this.data = data;
        this.wrap = $(passAdultTemp);
        this.order = order;
        this.child = [];
        this.lockIdCheck = false;
        this.errorMsg = {
            username: {2: '乘客姓名不能为空', 3: '请先填写完成人信息', 4: '请填写正确的姓名'},
            idCode: {
                2: '证件不能为空',
                3: '证件格式错误',
                4: '证件号码已经使用',
                5: '证件不符或未通过火车站核验',
                6: '身份验证不通过，请绑定12306账号购票，'
            },
            birth: {1: '请选择生日'}
        };
        this.errorTip = $(errorTemplate);
        this.init();
    }

    $.extend(Passenger.prototype, {
        init: function () {
            var self = this;
            var wrap = self.wrap;
            var data = self.data;
            var username = wrap.find('[name="username"]');
            var idType = wrap.find('[name="idType"]');
            var idCode = wrap.find('[name="idCode"]');
            var birthYear = wrap.find('[name="year"]');
            var birthMonth = wrap.find('[name="month"]');
            var birthDate = wrap.find('[name="date"]');
            var passTip = wrap.find('.J_PassTip');
            var passTipLabel = wrap.find('.J_PassTipLabel');
            var addChildBtn = wrap.find('.J_AddChild');
            var serialNum = wrap.find('.J_PassSerial');
            var deleteBtn = wrap.find('.J_PassDelete');
            var birthWrap = wrap.find('.J_BirthField');
            var J_TipUserName = wrap.find('.J_TipUserName');
            var J_TipUserNameC = wrap.find('.J_TipUserNameC');
            var J_TipIdType = wrap.find('.J_TipIdType');
            var J_TipIdTypeC = wrap.find('.J_TipIdTypeC');
            var largeId = wrap.find('.J_LargeId');
            var dateSelector;
            var birthReg = /\d{6}(\d{4})(\d{2})(\d{2})[\da-zA-Z]{4}/g;

            function changeIdType() {
                var type = idType.val();
                if ($.trim(idCode.val())) {
                    idCode.trigger('blur');
                }
                if (type === '1') {
                    birthWrap.hide();
                } else {
                    birthWrap.show();
                }
                if ($.trim(username.val())) {
                    username.trigger('blur');
                }
            }

            J_TipUserName.hover(function () {
                J_TipUserNameC.show();
            }, function () {
                J_TipUserNameC.hide();
            });
            J_TipIdType.hover(function () {
                J_TipIdTypeC.show();
            }, function () {
                J_TipIdTypeC.hide();
            });
            idCode.keyup(function () {
                var val = $.trim(this.value);
                if (val) {
                    val = val.replace(/(\d{4})/g, '$1 ').replace(/\s*$/, '');
                    largeId.html(val);
                    largeId.show();
                }
            });
            idCode.keydown(function () {
                largeId.hide();
            });
            username.blur(function () {
                var val = $.trim(this.value);
                var res = $.TN_checkNameForTicketPassager(val, self.idType.val());
                if (res === 1) {
                    self.removeError('username');
                    J_TipUserName.show();
                    self.newCheckIdvalid();
                    self.updateName();
                } else {
                    J_TipUserName.hide();
                    self.addError('username', res);
                }
            });
            idCode.blur(function () {
                largeId.hide();
                var val = $.trim(this.value);
                var res = self.checkIdCode(val);
                if (res === 1) {
                    self.removeError('idCode');
                    J_TipIdType.show();
                    if (self.idType.val() == '1') {
                        self.newCheckIdvalid();
                    }
                    if (self.idType.val() == '1' && val.length === 18) {
                        self.dateSelector.set(val.substring(6, 10) + '-' + parseInt(val.substring(10, 12), 10) + '-' + parseInt(val.substring(12, 14), 10));
                        self.removeError('birth');
                    }
                    self.updateName();
                } else {
                    J_TipIdType.hide();
                    self.addError('idCode', res);
                }
                self.order.checkSameCode();
            });
            idType.change(changeIdType);
            wrap.hover(function () {
                if (!self.ishideDelete) {
                    deleteBtn.show();
                }
            }, function () {
                if (!self.ishideDelete) {
                    deleteBtn.hide();
                }
            });
            deleteBtn.click(function () {
                self.order.removePass(self);
            });
            passTipLabel.hover(function () {
                passTip.addClass('hover');
            }, function () {
            });
            passTip.hover(function () {
            }, function () {
                passTip.removeClass('hover');
            });
            addChildBtn.click(function () {
                self.addChild();
            });
            dateSelector = new DateSelector(birthYear, birthMonth, birthDate);
            dateSelector.bind('change', function () {
                var date = this.get();
                if (!date) {
                    self.addError('birth', 1);
                } else {
                    self.removeError('birth');
                }
            });
            if (data) {
                username.val(data.name);
                idType.val(data.psptType);
                idCode.val(data.psptId);
                dateSelector.set(data.birthday);
            }
            self.items = {
                username: {wrap: username.parent(), input: username},
                idCode: {wrap: idCode.parent(), input: idCode},
                birth: {wrap: birthWrap, input: birthDate.next()}
            }
            self.idCode = idCode;
            self.idType = idType;
            self.username = username;
            self.serialNum = serialNum;
            self.addChildBtn = addChildBtn;
            self.dateSelector = dateSelector;
            self.birthWrap = birthWrap;
            self.deleteBtn = deleteBtn;
            wrap.appendTo(passGroups);
            changeIdType();
            passengers.push(self);
        }, addChild: function () {
            var child;
            var res = $.TN_checkNameForTicketPassager(this.username.val(), this.idType.val());
            if (res == 2) {
                this.addError('username', 3);
                return;
            }
            if (passengers.length < this.order._total) {
                child = new Child(this);
                this.appendChild(child.wrap);
                child.set({
                    name: $.trim(this.username.val()),
                    psptType: this.idType.find("option:selected").text(),
                    psptId: $.trim(this.idCode.val())
                });
                this.child.push(child);
                if (this.order.needSelect == true) {
                    this.order.selectSeat.numChange();
                }
                this.order.getPromotionList();
                this.order.update();
            }
        }, checkIdvalid: function () {
            var self = this;
            var wrap = self.wrap;
            var username = $.trim(self.username.val());
            var idCode = $.trim(self.idCode.val());
            var idType = $.trim(self.idType.val());
            var J_TipIdType = wrap.find('.J_TipIdType');
            if (idType == '1' && username && idCode) {
                $.get('/tn?r=train/TrainTicket/CheckIDCard', {
                    name: username,
                    IDCard: idCode,
                    absId: order.absId,
                    userId: userId
                }, function (data) {
                    if (data && data.code == 500) {
                        if (data.isFalsely == 1) {
                            J_TipIdType.hide();
                            self.addError('idCode', 6);
                            var J_bind12306 = wrap.find('.btn_bind12306');
                            J_bind12306.unbind("click").on("click", function () {
                                if (userId > 0) {
                                    order.openlogin12306();
                                } else {
                                    var _msg = "请先登录途牛账号";
                                    msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class=""><h3><div class="msg" style="text-align:center;position:relative;margin-left:120px;">' + _msg + '</div></h3></div></div>';
                                    var falid_dialog = $.layer({
                                        type: 1,
                                        guide: 2,
                                        title: false,
                                        btns: 0,
                                        area: ["430px", "120px"],
                                        border: [6, 1, '#c4c4c5'],
                                        page: {html: msg}
                                    });
                                    setTimeout(function () {
                                        layer.close(falid_dialog);
                                        order.openDialog();
                                    }, 3000);
                                }
                            });
                        } else {
                            J_TipIdType.hide();
                            self.addError('idCode', 5);
                        }
                    } else if (data && data.code == 200) {
                        self.removeError('idCode', 2);
                        self.removeError('idCode', 3);
                        self.removeError('idCode', 5);
                        self.removeError('idCode', 6);
                        if (self.items['idCode'].error == false) {
                            J_TipIdType.show();
                        }
                    }
                }, 'json');
            }
        }, newCheckIdvalid: function () {
            var self = this;
            var wrap = self.wrap;
            var username = $.trim(self.username.val());
            var idCode = $.trim(self.idCode.val());
            var idType = $.trim(self.idType.val());
            var J_TipIdType = wrap.find('.J_TipIdType');
            if (idType == '1' && username && idCode && !self.lockIdCheck) {
                if (self.lockIdCheck == false) {
                    self.lockIdCheck = true;
                }
                $.get('/tn?r=train/TrainTicket/IsIDCardValuableAjax', {
                    name: username,
                    IDCard: idCode,
                    userId: userId,
                    identityType: idType
                }, function (data) {
                    if (self.lockIdCheck == true) {
                        self.lockIdCheck = false;
                    }
                    if (data && data.data) {
                        $(wrap).find('.passenger-adult').attr('verificationStatus', data.data[0].verificationStatus);
                    } else {
                        $(wrap).find('.passenger-adult').attr('verificationStatus', 1);
                    }
                    if (data && data.success) {
                        if (data.data[0].verificationStatus == 3) {
                            J_TipIdType.hide();
                            self.addError('idCode', 6);
                            var J_bind12306 = wrap.find('.btn_bind12306');
                            J_bind12306.unbind("click").on("click", function () {
                                if (userId > 0) {
                                    order.openlogin12306();
                                } else {
                                    var _msg = "请先登录途牛账号";
                                    msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class=""><h3><div class="msg" style="text-align:center;position:relative;margin-left:120px;">' + _msg + '</div></h3></div></div>';
                                    var falid_dialog = $.layer({
                                        type: 1,
                                        guide: 2,
                                        title: false,
                                        btns: 0,
                                        area: ["430px", "120px"],
                                        border: [6, 1, '#c4c4c5'],
                                        page: {html: msg}
                                    });
                                    setTimeout(function () {
                                        layer.close(falid_dialog);
                                        order.openDialog();
                                    }, 3000);
                                }
                            });
                            J_TipIdType.hide();
                            self.addError('idCode', 5);
                        } else {
                            J_TipIdType.show();
                            self.removeError('idCode', 5);
                        }
                    } else {
                        self.removeError('idCode', 2);
                        self.removeError('idCode', 3);
                        self.removeError('idCode', 5);
                        self.removeError('idCode', 6);
                        if (self.items['idCode'].error == false) {
                            J_TipIdType.show();
                        }
                    }
                }, 'json');
            } else {
                $(wrap).find('.passenger-adult').attr('verificationStatus', 1);
            }
        }, removeChild: function (item) {
            var children = this.child;
            var index = this.getChildIndex(item);
            if (index !== -1) {
                children.splice(index, 1);
                item.remove();
            }
        }, getChildIndex: function (child) {
            var children = this.child;
            for (var i = 0; i < children.length; i++) {
                if (child === children[i]) {
                    return i;
                }
            }
            return -1;
        }, appendChild: function (ele) {
            this.wrap.find('.pass_group_bt').before(ele);
        }, checkUsername: function (val) {
            val = $.trim(val);
            return val === '' ? 2 : 1;
        }, checkIdCode: function (val) {
            var type = this.idType.val();
            var res = 1;
            var ids;
            val = $.trim(val);
            switch (type) {
                case'1':
                    res = $.TN_checkIdCard(val);
                    break;
                case'4':
                    res = $.TN_checkHKMacao(val);
                    break;
                case'7':
                    res = $.TN_checkTW(val);
                    break;
                case'2':
                    res = $.TN_checkPassport(val);
                    break;
            }
            if (res == 1) {
            }
            return res;
        }, get: function () {
            var self = this;
            var res = {};
            var items = this.items;
            var flag = true;
            var type, item;
            var childInfo;
            var children = [];
            var birth;
            for (type in items) {
                item = items[type];
                if (item.error === void (0)) {
                    item.input.trigger('blur');
                    if (item.error === true) {
                        flag = false;
                    } else {
                        res[type] = $.trim(item.input.val())
                    }
                } else if (item.error === true) {
                    flag = false;
                } else {
                    res[type] = $.trim(item.input.val())
                }
            }
            if (this.child) {
                $.each(this.child, function (index, child) {
                    childInfo = child.get()
                    if (childInfo === false) {
                        flag = false;
                    } else {
                        children.push(childInfo);
                    }
                });
            } else {
                children = false;
            }
            birth = this.dateSelector.get();
            if (!birth) {
                flag = false;
                self.addError('birth', 1);
            }
            if (flag === false) {
                return false;
            }
            res.name = $.trim(this.username.val());
            res.psptType = this.idType.val();
            res.idTypeName = this.idType.find(':selected').text();
            res.psptId = $.trim(this.idCode.val());
            res.birthday = birth;
            res.childTourists = children;
            return res;
        }, set: function (data) {
            this.username.val(data.name);
            this.idType.val(data.psptType);
            this.idCode.val(data.psptId);
            this.dateSelector.set(data.birthday);
            this.username.trigger('blur');
            this.idType.trigger('change');
            this.idCode.trigger('blur');
            this.dateSelector.trigger('change');
        }, clear: function () {
            this.username.val('');
            this.idType.val('');
            this.idCode.val('');
            this.dateSelector.set('2014-1-1');
        }, remove: function () {
            var index;
            var children = this.child;
            this.wrap.remove();
            this.order = null;
            if (children) {
                while (children[0]) {
                    children[0].remove();
                    children.splice(0, 1);
                }
            }
            index = getPassIndex(this);
            passengers.splice(index, 1);
            this.trigger('remove', this);
        }, updateIndex: function (index) {
            this.serialNum.text(index);
        }, updateName: function () {
            var username = this.username.val();
            var psptId = this.idCode.val();
            this.wrap.children('.pass_item_child').find('.usernameAdult-child').text(username);
            this.wrap.children('.pass_item_child').find('.psptIdAdult-child').text(psptId);
        }
    }, commonProto);

    function Child(adult) {
        this._type = 'child';
        this.wrap = $(passChildTemp);
        this.adult = adult;
        this.errorMsg = {
            username: {2: '儿童姓名不能为空'},
            'birth': {1: '请选择生日', 2: '此乘客已满18周岁，请购买成人票'}
        };
        this.errorTip = $(errorTemplate);
        this.init();
    }

    $.extend(Child.prototype, {
        init: function () {
            var self = this;
            var wrap = self.wrap;
            var username = wrap.find('[name="username-child"]');
            var year = wrap.find('[name="year"]');
            var month = wrap.find('[name="month"]');
            var date = wrap.find('[name="date"]');
            var serialNum = wrap.find('.J_PassSerial');
            var deleteBtn = wrap.find('.J_PassDelete');
            var dateSelector;
            username.blur(function () {
                var val = this.value;
                var res = $.TN_checkNameForTicketPassager(val, self.adult.idType.val());
                if (res === 1) {
                    self.removeError('username');
                } else {
                    self.addError('username', res);
                }
            });
            wrap.hover(function () {
                deleteBtn.show();
            }, function () {
                deleteBtn.hide();
            });
            deleteBtn.click(function () {
                self.adult.removeChild(self);
            });
            dateSelector = new DateSelector(year, month, date);
            dateSelector.bind('change', function () {
                var date = this.get();
                if (!date) {
                    self.addError('birth', 1);
                } else if (isAdult(date)) {
                    self.addError('birth', 2);
                } else {
                    self.removeError('birth');
                }
            });
            self.items = {
                username: {wrap: username.parent(), input: username},
                birth: {wrap: date.parents('.field'), input: date.next()}
            }
            self.year = year;
            self.month = month;
            self.date = date;
            self.username = username;
            self.serialNum = serialNum;
            self.dateSelector = dateSelector;
            self.deleteBtn = deleteBtn;
            self.usernameAdult = wrap.find('.usernameAdult-child');
            self.psptIdAdult = wrap.find('.psptIdAdult-child');
            self.psptTypeAdult = wrap.find('.psptTypeAdult-child');
            wrap.appendTo(passGroups);
            if (order.buyInsuranceFlag) {
                $(".dependIns", wrap).show();
            }
            passengers.push(self);
            order.update();
        }, remove: function () {
            var index;
            this.wrap.remove();
            this.adult = null;
            index = getPassIndex(this);
            passengers.splice(index, 1);
            order.getPromotionList();
            if (order.needSelect == true) {
                order.selectSeat.numChange();
            }
            order.update();
        }, checkUsername: function (val) {
            val = $.trim(val);
            return val === '' ? 2 : 1;
        }, get: function () {
            var self = this;
            var res = {};
            var items = this.items;
            var type, item;
            var birth;
            if (order.buyInsuranceFlag) {
                for (type in items) {
                    item = items[type];
                    if (item.error === void (0)) {
                        item.input.trigger('blur');
                        if (item.error === true) {
                            return false;
                        } else {
                            res[type] = $.trim(item.input.val());
                        }
                    } else if (item.error === true) {
                        return false;
                    } else {
                        res[type] = $.trim(item.input.val());
                    }
                }
                res.name = $.trim(this.username.val());
                birth = this.dateSelector.get();
                if (!birth) {
                    self.addError('birth', 1);
                    return false;
                } else if (isAdult(birth)) {
                    self.addError('birth', 2);
                    return false;
                }
                res.birthday = birth;
            } else {
                res.name = self.adult.username.val();
            }
            res.psptId = self.psptId;
            return res;
        }, set: function (data) {
            if (data.name) {
                this.usernameAdult.text(data.name);
            }
            if (data.psptId) {
                this.psptIdAdult.text(data.psptId);
                this.psptId = data.psptId;
            }
            if (data.psptType) {
                this.psptTypeAdult.text(data.psptType);
            }
            if (data.birthday) {
                this.dateSelector.set(data.birthday);
                this.dateSelector.trigger('change');
            }
        }, updateIndex: function (index) {
            this.serialNum.text(index);
        }
    }, commonProto);

    function Contacts(data, order) {
        this.data = data || null;
        this.order = order;
        this.wrap = $('#J_PassFavors');
        this.init();
    }

    $.extend(Contacts.prototype, {
        init: function () {
            var self = this;
            var data = self.data;
            if (data) {
                self.buildList();
            }
        }, load: function (data) {
            this.data = data;
            this.buildList();
        }, buildList: function () {
            var self = this;
            var data = self.data;
            for (var key in data) {
                self.buildItem(data[key]);
            }
        }, buildItem: function (data) {
            var self = this;
            var wrap = self.wrap;
            var order = self.order;
            var itemEle = $('<div />').addClass('pass_favor_item');
            var labelEle = $('<label />').text(data.name).appendTo(itemEle);
            var checkboxEle = $('<input />').attr('type', 'checkbox').prependTo(labelEle);
            var pass;
            checkboxEle.change(function () {
                var checkbox = this;
                if (checkbox.checked && !pass) {
                    pass = order.addPass(data);
                    if (pass) {
                        pass.bind('remove', function () {
                            checkbox.checked = false;
                        });
                    } else {
                        checkbox.checked = false;
                    }
                } else if (pass) {
                    if (pass) {
                        order.removePass(pass);
                        pass = null;
                    }
                }
            });
            wrap.append(itemEle);
        }
    });

    function Insurance(data, order) {
        this.data = data;
        this.order = order;
        this.needInvoice = false;
        this.checkFalg = true;
        this.defaultIndex = parseInt(data.index, 10) + 1;
        this.errorMsg = {
            receiver: {2: '收件人不能为空'},
            streetAdd: {2: '街道地址不能为空'},
            postCode: {2: '邮编不能为空', 3: '邮编格式不符，请重新输入'},
            mobile: {2: '手机号不能为空', 3: '手机号格式不符，请重新输入'}
        };
        this.errorTip = $(errorTemplate);
        this.init();
    }

    $.extend(Insurance.prototype, commonProto, {
        init: function () {
            var self = this;
            self.container = $("#insureSelect");
            self.select = $("select[name='insurance']", self.container);
            self.invoinceArea = $("#J_Voice");
            self.needInvoice = $("input[name='needInvoice']", self.invoinceArea);
            var receiver = $("input[name='receiver']", self.invoinceArea);
            var streetAdd = $("input[name='streetAdd']", self.invoinceArea);
            var postCode = $("input[name='postCode']", self.invoinceArea);
            var mobile = $("input[name='mobile']", self.invoinceArea);
            self.items = {
                receiver: {wrap: receiver.parent(), input: receiver},
                streetAdd: {wrap: streetAdd.parent(), input: streetAdd},
                postCode: {wrap: postCode.parent(), input: postCode},
                mobile: {wrap: mobile.parent(), input: mobile}
            };
            self.select.find("option").eq(self.defaultIndex).attr("selected", "selected");
            if (self.defaultIndex > 0) {
                self.order.buyInsuranceFlag = true;
                self.order.insurancePrice = self.select.val().split("_")[1];
                self.invoinceArea.show();
                var selectIndex = self.defaultIndex - 1;
                var tipContent = self.data.insuranceList[selectIndex].insuranceInstructionml;
                $('#J_insureTip_content').html(tipContent);
                $('#J_insure_tip').css('display', 'inline-block');
            } else {
                $('#J_insure_tip').hide();
            }
            self.select.change(function () {
                var selectIndex = self.select.get(0).selectedIndex;
                if (0 != selectIndex) {
                    selectIndex = selectIndex - 1;
                    var tipContent = self.data.insuranceList[selectIndex].insuranceInstructionml;
                    $('#J_insureTip_content').html(tipContent);
                    $('#J_insure_tip').css('display', 'inline-block');
                } else {
                    $('#J_insure_tip').hide();
                }
                self.trigger("change");
            });
            $('#J_insure_tip').hover(function () {
                $('#J_insureTip_content').show();
            });
            $('body').click(function () {
                $('#J_insureTip_content').hide();
            });
            self.needInvoice.click(function () {
                if ($(this)[0].checked) {
                    $(".dependInvoice", self.invoinceArea).show();
                } else {
                    $(".dependInvoice", self.invoinceArea).hide();
                }
            })
        }, checkInvoice: function () {
            var self = this;
            var flag = true;
            if (self.needInvoice[0].checked) {
                var receiver_check = $.TN_checkNameForTicketPassager(self.items.receiver.input.val());
                var streetAdd_check = $.TN_checkNameForTicketPassager(self.items.streetAdd.input.val());
                var postCode_check = $.TN_checkPostcode(self.items.postCode.input.val());
                var mobile_check = $.TN_checkTel(self.items.mobile.input.val());
                if (receiver_check == 2) {
                    self.addError('receiver', receiver_check);
                    flag = false;
                } else {
                    self.removeError('receiver', '2');
                }
                if (streetAdd_check == 2) {
                    self.addError('streetAdd', streetAdd_check);
                    flag = false;
                } else {
                    self.removeError('streetAdd', '2');
                }
                if (postCode_check != 1) {
                    self.addError('postCode', postCode_check);
                    flag = false;
                } else {
                    self.removeError('postCode', '2');
                    self.removeError('postCode', '3');
                }
                if (mobile_check != 1) {
                    self.addError('mobile', mobile_check);
                    flag = false;
                } else {
                    self.removeError('mobile', '2');
                    self.removeError('mobile', '3');
                }
            }
            return flag;
        }, get: function () {
            var self = this;
            var items = this.items;
            var insuranceInfo = {};
            if (!order.buyInsuranceFlag) {
                return insuranceInfo;
            } else {
                insuranceInfo.insuranceResourceId = self.select.val().split("_")[0];
                insuranceInfo.insurancePrice = parseInt(self.select.val().split("_")[1], 10);
                if (self.needInvoice[0].checked) {
                    if (self.checkInvoice()) {
                        var province_invoice = $("select[name='province_invoice']", self.invoinceArea).val();
                        var city_invoice = $("select[name='city_invoice']", self.invoinceArea).val();
                        var area_invoice = $("select[name='area_invoice']", self.invoinceArea).val();
                        insuranceInfo.receiverName = items.receiver.input.val();
                        insuranceInfo.address = province_invoice + city_invoice + area_invoice + items.streetAdd.input.val();
                        insuranceInfo.telNum = items.mobile.input.val();
                        insuranceInfo.zipCode = items.postCode.input.val();
                    } else {
                        return false;
                    }
                }
                return insuranceInfo;
            }
        }
    });

    function SelectSeat(order) {
        this.order = order;
        var seat = this.order.ticket.seat;
        var J_Select_content = $(".J_Select_content");
        var num = this.order.selectSeatNum;
        var html = $('#T_SelectSleeper').html();
        if (seat == 0 || seat == 2 || seat == 3 || seat == 8) {
            html = '';
        }
        J_Select_content.html(html);
        this.order.needSelect = true;
        this.container = $(".J_Select_content");
        $(".J_select_num").html(num);
        J_Select_content.show();
        this.init();
    }

    $.extend(SelectSeat.prototype, commonProto, {
        init: function () {
            var self = this;
            var container = self.container;
            var J_add = $("#J_add", container);
            var J_reduce = $("#J_reduce", container);
            J_add.click(function () {
                var selectNum = parseInt($(".J_select_num").html());
                var passLen = passengers.length;
                if (selectNum >= passLen) {
                    J_add.addClass("calc_no");
                    $(".J_select_num").html(passLen);
                } else {
                    if (selectNum == (passLen - 1)) {
                        J_add.addClass("calc_no");
                    }
                    self.order.selectSeatNum++;
                    var num = self.order.selectSeatNum;
                    $(".J_select_num").html(num);
                    J_reduce.removeClass('calc_no');
                }
            });
            J_reduce.click(function () {
                var selectNum = parseInt($(".J_select_num").html());
                if (selectNum <= 1) {
                    J_reduce.addClass("calc_no");
                    $(".J_select_num").html(1);
                } else {
                    if (selectNum == 2) {
                        J_reduce.addClass("calc_no");
                    }
                    self.order.selectSeatNum--;
                    var num = self.order.selectSeatNum;
                    $(".J_select_num").html(num);
                    J_add.removeClass('calc_no');
                }
            });
            self.numChange();
        }, numChange: function () {
            var self = this;
            var container = self.container;
            var J_add = $("#J_add", container);
            var J_reduce = $("#J_reduce", container);
            var passLen = passengers.length;
            if (self.order.selectSeatNum >= passLen) {
                self.order.selectSeatNum = passLen;
                $(".J_select_num").html(passLen);
                J_add.addClass("calc_no");
            } else {
                J_add.removeClass("calc_no");
            }
            if (self.order.selectSeatNum == 1) {
                J_reduce.addClass("calc_no");
            } else {
                J_reduce.removeClass("calc_no");
            }
        }, get: function () {
            var self = this;
            var data = {};
            var seat = this.order.ticket.seat;
            var selectSeatNum = self.order.selectSeatNum;
            var acceptOthers = true;
            if (false == $("input[name='acceptSelect']")[0].checked) {
                acceptOthers = false;
            }
            if (seat == 0 || seat == 2 || seat == 3 || seat == 8) {
                data.isConnecting = true;
                data.connectingNum = selectSeatNum;
                data.acceptOthers = acceptOthers;
            } else {
                data.lowerNum = selectSeatNum;
                data.acceptOthers = acceptOthers;
            }
            return data;
        }
    });

    function Promotion(data, order) {
        this.data = data;
        this.order = order;
        this.template = _.template($('#T_PromotionContent').html());
        this.wrap = $("#J_Promotion");
        this.init();
    }

    $.extend(Promotion.prototype, commonProto, {
        init: function () {
            var self = this;
            var container = self.container = $(self.template({"list": self.data}));
            var radios = self.radios = $("input[name='promotionCheck']", container);
            radios.change(function () {
                self.trigger("change");
            });
            var passTipLabel = container.find('.promotion_desc');
            passTipLabel.hover(function () {
                $(this).parents(".promotionUnit").addClass('hover');
            }, function () {
                $(this).parents(".promotionUnit").removeClass('hover');
            });
            self.wrap.find('#J_Promotion_content').html(container);
            self.wrap.show();
        }, get: function () {
            var self = this;
            var data = {};
            var selectedEle = self.container.find("input:checked");
            if (selectedEle.length > 0) {
                data.id = selectedEle.attr("data-id");
                data.price = parseInt(selectedEle.attr("data-price"), 10);
            }
            return data;
        }
    });
    var passGroups = $('#J_PassGroups');
    var passAdultTemp = $('#T_PassengerAdult').html();
    var passChildTemp = $('#T_PassengerChild').html();
    var seatTypeTipTemplate = $('#T_TypeTip').html();
    var orderConfirmTemplate = $('#T_OrderConfirm').html();
    var errorTemplate = '<span class="error"><span class="icon error_icon"></span><span class="error_con"></span></span>';
    template.helper('getInt', function (num) {
        return parseInt(num) || 0;
    });
    var orderConfirmRender = template.compile(orderConfirmTemplate);
    var passengers = [];
    var isSavingOrder = false;
    var today = str2Date(window.TKToday) || new Date();
    today.setHours(0);
    today.setMinutes(0);
    today.setSeconds(0);
    today.setMilliseconds(0);

    function Order() {
        this.passengers = [];
        this.contact = null;
        this.checkTerm = false;
        this._total = 5;
        this.absId = 0;
        this.isGettingInfo = false;
        this.buyInsuranceFlag = false;
        this.insurancePrice = 0;
        this.promotionPrice = 0;
        this.needSend = false;
        this.needSelect = false;
        this.deliveryType = 0;
        this.selectSeatNum = 1;
        this.username12306 = "";
        this.password12306 = "";
        this.init();
    }

    $.extend(Order.prototype, {
        init: function () {
            var self = this;
            var addPassBtn = $('#J_AddPass');
            var passWrap = $('#J_PassGroups');
            var checkTermBtn = $('#J_CheckTerm');
            var termWrap = $('.J_OrderNext');
            var submitBtn = $('#J_Submit');
            var restNum = $('#J_RestNum');
            var summary = $('#J_Side');
            var sideTip = $('#J_SideTip');
            var loginBtn = $('#J_LoginBtn');
            var readTermBtn = $('.tk_read_term');
            var termContent = $('#T_TermContent').html();
            var standingTicket = $('#J_AcceptStandingTicket').find("input[type='checkbox']");
            var priceTip = $("#J_Pass").find('.J_PriceTip');
            var priceTipLabel = $("#J_Pass").find('.J_PriceTipLabel');
            var J_LoginBtn_12306 = $("#J_LoginBtn_12306");
            var needSelect = $("input[name='needSelect']");
            var J_Select_content = $(".J_Select_content");
            var J_Contact_title = $("#J_Contact_title");
            var J_Send_title = $("#J_Send_title");
            var selfGetTicket = $(".selfGetTicket");
            var win = $(window);
            var offset = summary.offset();
            var fixed = false;
            addPassBtn.click(function () {
                self.addPass();
            });
            checkTermBtn.click(function () {
                if (self.checkTerm) {
                    termWrap.removeClass('checked');
                    self.checkTerm = false;
                } else {
                    termWrap.addClass('checked');
                    self.checkTerm = true;
                }
            });
            submitBtn.click(function () {
                var res = self.get();
                if ($('#J_Contact_title').hasClass('contact_title_click')) {
                    checkPass = true;
                }
                if (!checkPass) return false;
                if (res && self.checkTerm) {
                    if (self.checkPassenger()) {
                        self.checkOrder(res);
                    }
                }
            });
            loginBtn.click(function () {
                self.openDialog();
            });
            self.init12306();
            self.checkSupportDispatch();
            J_LoginBtn_12306.click(function () {
                if (userId > 0) {
                    self.openlogin12306();
                } else {
                    var _msg = "请先登录途牛账号";
                    msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class=""><h3><div class="msg" style="text-align:center;position:relative;margin-left:120px;;">' + _msg + '</div></h3></div></div>';
                    var falid_dialog = $.layer({
                        type: 1,
                        guide: 2,
                        title: false,
                        btns: 0,
                        area: ["430px", "120px"],
                        border: [6, 1, '#c4c4c5'],
                        page: {html: msg}
                    });
                    setTimeout(function () {
                        layer.close(falid_dialog);
                        self.openDialog();
                    }, 3000);
                }
            });
            readTermBtn.click(function () {
                $.layer({
                    type: 1,
                    guide: 2,
                    title: '产品预订协议',
                    closeBtn: [0, true],
                    btns: 0,
                    area: ['800px', 'auto'],
                    border: [6, 1, '#c4c4c5'],
                    page: {html: termContent}
                });
            });
            needSelect.click(function () {
                if ($(this)[0].checked) {
                    self.selectSeat = new SelectSeat(self);
                    J_Send_title.click();
                } else {
                    self.selectSeatNum = 1;
                    self.needSelect = false;
                    J_Select_content.hide();
                }
            });
            J_Contact_title.click(function () {
                if (self.needSend == true) {
                    if (self.needSelect == true) {
                        $(".shade_note").show();
                        $("#J_shiftContact").show();
                    } else {
                        $("#J_Send_content").hide();
                        self.needSend = false;
                        J_Contact_title.addClass("contact_title_click");
                        J_Send_title.removeClass("send_title_click");
                        $("#J_Contact_content").show();
                        self.calcPrice();
                    }
                }
            });
            $("#J_shiftContact .errorBtn").click(function () {
                $("#J_shiftContact").hide();
                $(".shade_note").hide();
            });
            $("#J_shiftContact .noteCloseBtn").click(function () {
                $("#J_shiftContact").hide();
                $(".shade_note").hide();
            });
            $("#J_shiftContact .successBtn").click(function () {
                self.needSend = false;
                $("#J_Send_content").hide();
                J_Contact_title.addClass("contact_title_click");
                J_Send_title.removeClass("send_title_click");
                $("#J_shiftContact").hide();
                $(".shade_note").hide();
                $("#J_Contact_content").show();
                if (self.needSelect == true) {
                    self.needSelect = false;
                    needSelect[0].checked = false;
                    self.selectSeatNum = 1;
                    J_Select_content.hide();
                }
                self.calcPrice();
            });
            selfGetTicket.click(function () {
                J_Contact_title.click();
            });
            J_Send_title.click(function () {
                if (self.needSend == false) {
                    self.needSend = true;
                    J_Send_title.addClass("send_title_click");
                    J_Contact_title.removeClass("contact_title_click");
                    $("#J_Contact_content").hide();
                    $("#J_Send_content").show();
                    self.deliveryType = 1;
                    self.sendFee = parseInt($('#sendFee').val());
                    self.purchasingFee = parseInt($('#purchasingFee').val());
                    self.calcPrice();
                }
            });
            $("#tip_icon_contact").hover(function () {
                $("#J_tip_contact").show();
            }, function () {
                $("#J_tip_contact").hide();
            });
            $("#tip_icon_send").hover(function () {
                $("#J_tip_send").show();
            }, function () {
                $("#J_tip_send").hide();
            });
            $("#J_select_tip").hover(function () {
                $("#J_selectTip_content").show();
            }, function () {
                $(this).removeClass("hover");
                $("#J_selectTip_content").hide();
            });
            priceTipLabel.hover(function () {
                priceTip.addClass('hover');
            }, function () {
            });
            priceTip.hover(function () {
            }, function () {
                priceTip.removeClass('hover');
            });
            var adjustPosition = function () {
                var pageHeight = window.innerHeight;
                if (typeof pageHeight != "number") {
                    if (document.compatMode == "CSS1Compat") {
                        pageHeight = document.documentElement.clientHeight;
                    } else {
                        pageHeight = document.body.clientHeight;
                    }
                }
                var $ele = summary, iHeight = $ele.height(), eEle = $('.J-flag').length > 0 ? $('.J-flag')[0] : null,
                    iRectBrowserTop = eEle && eEle.getBoundingClientRect().top;
                var iMinTop = (iHeight < pageHeight) ? 0 : pageHeight - iHeight;
                if ((iHeight - pageHeight) > 0 && iRectBrowserTop <= iMinTop) {
                    $ele.css({position: 'fixed', top: iMinTop, left: '50%', marginLeft: 310});
                }
                if (iRectBrowserTop >= 0) {
                    $ele.css({position: 'relative', left: 0, marginLeft: 0, top: ''});
                }
                if ((iHeight - pageHeight) < 0 && iRectBrowserTop < 0) {
                    $ele.css({position: 'fixed', top: 0, left: '50%', marginLeft: 310});
                }
            };
            win.scroll(adjustPosition);
            if (self.checkTerm) {
                termWrap.addClass('checked');
            }
            this.contact = new Contact(this);
            this.pricePlans = window.pricePlans;
            this.ticket = new Ticket(window.ticketData);
            this.sendTicket = new SendTicket(this);
            this.passWrap = passWrap;
            this.restNum = restNum;
            this.sideTip = sideTip;
            this.fixed = fixed;
            this.summary = summary;
            this.standingTicket = standingTicket;
            if (this.pricePlans) {
                $.each(this.pricePlans, function (i, item) {
                    if (item.seatType == self.ticket.seat && item.leftTicket && item.leftTicket < 5) {
                        self._total = item.leftTicket;
                        self.restNum.text(self._total - passengers.length);
                    }
                });
            }
            this.contact.bind('registed', function () {
                self.getInfo();
            });
            this.contact.bind('login', function () {
                self.openDialog();
            });
            this.ticket.bind('change', function () {
                if (openSelectTicket == true) {
                    if (self.ticket.seat != 4 && self.ticket.seat != 5 && self.ticket.seat != 6) {
                        self.needSelect = false;
                        $("#J_Select").hide();
                        $(".J_Select_content").hide();
                        needSelect[0].checked = false;
                    } else {
                        $("#J_Select").show();
                    }
                    if (self.needSelect == true) {
                        self.selectSeat = new SelectSeat(self);
                    }
                }
                self.update();
            });
            if (self.ticket.seat != 4 && self.ticket.seat != 5 && self.ticket.seat != 6) {
                if (openSelectTicket == true) {
                    self.needSelect = false;
                    $("#J_Select").hide();
                    $(".J_Select_content").hide();
                }
            }
            if (isLogin) {
                self.afterLogin();
            }
            this.addPass();
            this.contacts = new Contacts(window.contactInfo, this);
            if (insuranceInfo) {
                this.insurance = new Insurance(insuranceInfo, this);
                this.insurance.bind("change", function () {
                    var insuranceType = self.insurance.select.val();
                    if (insuranceType == '-1') {
                        $(".dependIns").hide();
                        self.buyInsuranceFlag = false;
                        self.insurance.invoinceArea.hide();
                    } else {
                        self.insurancePrice = insuranceType.split("_")[1];
                        $(".dependIns").show();
                        self.buyInsuranceFlag = true;
                        self.insurance.invoinceArea.show();
                    }
                    self.update();
                });
            }
            if (promotionList && promotionList.length > 0) {
                this.promotion = new Promotion(promotionList, this);
                this.promotion.bind("change", function () {
                    var selectedPromotion = self.promotion.get();
                    self.promotionPrice = selectedPromotion ? selectedPromotion.price : 0;
                    self.calcPrice();
                });
                this.promotion.radios[0].checked = true;
                this.promotion.trigger("change");
            }
            this.update();
            checkTermBtn.trigger('click');
            $("#J_LoginBtn_12306_logout").click(function () {
                $(".shade_note").show();
                $("#J_unbind12306").show();
            });
            $("#J_unbind12306 .errorBtn").click(function () {
                $(".shade_note").hide();
                $("#J_unbind12306").hide();
            });
            $("#J_unbind12306 .successBtn").click(function () {
                self.logout12306();
                $(".shade_note").hide();
                $("#J_unbind12306").hide();
            });
            $("#J_unbind12306 .noteCloseBtn").click(function () {
                $(".shade_note").hide();
                $("#J_unbind12306").hide();
            });
            $("#J_ShiftBtn_12306_logout").click(function () {
                $(".shade_note").show();
                $("#J_shift12306").show();
            });
            $("#J_shift12306 .errorBtn").click(function () {
                $(".shade_note").hide();
                $("#J_shift12306").hide();
            });
            $("#J_shift12306 .successBtn").click(function () {
                $(".shade_note").hide();
                $("#J_shift12306").hide();
                J_LoginBtn_12306.click();
            });
            $("#J_shift12306 .noteCloseBtn").click(function () {
                $(".shade_note").hide();
                $("#J_shift12306").hide();
            });
            $("#J_bindSuccess12306 .noteCloseBtn").click(function () {
                $(".shade_note").hide();
                $("#J_bindSuccess12306").hide();
            });
        }, errorWin: function (_msg, width, height) {
            msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class=""><h3><div class="msg" style="text-align:center;">' + _msg + '</div></h3></div><div class="dialog_btn dialog_btn2"><a class="btn_ok2">确定</a>   </div></div>';
            var falid_dialog = $.layer({
                type: 1,
                guide: 2,
                title: false,
                btns: 0,
                area: [width, height],
                border: [6, 1, '#c4c4c5'],
                page: {html: msg}
            });
            $("#xubox_layer" + falid_dialog).find(".btn_ok2").click(function () {
                layer.close(falid_dialog);
            })
        }, init12306: function () {
            var self = this;
            $.get('/tn?r=train/TrainTicket/Init12306', {}, function (json) {
                if (json && json.success) {
                    if (json.data && json.data.loginStatus == 1) {
                        self.absId = json.data.absId;
                        $("#J12306").hide();
                        $("#J_username12306").html(json.data.userName);
                        $("#J12306ed").show();
                    } else {
                    }
                }
            }, 'json')
        }, checkSupportDispatch: function () {
            var p = $('#province_send').val();
            var c = $('#city_send').val();
            var a = $('#area_send').val();
            if (0 != p && 0 != c && 0 != a) {
                var url = '/tn?r=train/TrainTicket/getDeliveryPriceByRegion';
                var param = {};
                param.departDate = window.ticketData.startDate;
                param.provinceId = $('#province_send option:selected').attr('addr-id');
                param.provinceName = $('#province_send option:selected').text();
                param.cityId = $('#city_send option:selected').attr('addr-id');
                param.cityName = $('#city_send option:selected').text();
                $.get(url, param, function (res) {
                    if (res.success && res.data && 1 == res.data.ifCanDelivery) {
                        var defaultFee = $('#sendFee').val();
                        if (0 != res.data.deliveryFee) defaultFee = res.data.deliveryFee;
                        deliveryFee_ = defaultFee;
                        $('#dispatchFeeDiv').css('display', 'block');
                        $('#deliveryFee').html('￥' + defaultFee);
                    } else {
                        $('.selectRegion').css('display', 'none');
                        $('.notSupportDispatch').css('display', 'inline');
                        checkPass = false;
                    }
                }, 'json');
            }
        }, addPass: function (data) {
            var self = this;
            var passes = this.passengers;
            var pass;
            pass = self.getFirstBlankPass();
            if (pass && data) {
                pass.set(data);
                return pass;
            } else {
                if (passengers.length >= this._total) {
                    return false;
                }
                pass = new Passenger(data, this);
                passes.push(pass);
                pass.newCheckIdvalid();
                this.appendPass(pass.wrap);
                this.getPromotionList();
                if (self.needSelect == true) {
                    self.selectSeat.numChange()
                }
                this.update();
                return pass;
            }
        }, getFirstBlankPass: function () {
            var passes = this.passengers;
            var pass;
            for (var i = 0; i < passes.length; i++) {
                pass = passes[i];
                if ($.trim(pass.username.val()) === '') {
                    return pass;
                }
            }
            return false;
        }, appendPass: function (ele) {
            this.passWrap.append(ele);
        }, removePass: function (pass) {
            var self = this;
            var passes = self.passengers;
            var dialog;
            var index;
            if (passes.length <= 1) {
                pass.clear();
                return false;
            }
            if (pass.child && pass.child.length) {
                dialog = $.layer({
                    guide: 2,
                    title: '提示',
                    type: 1,
                    page: {html: '<div class="dialog_tip">删除成人乘客会删除同行的儿童乘客，您确定要删除?</div>'},
                    border: [6, 1, '#c4c4c5'],
                    btns: 2,
                    btn: ['确定', '取消'],
                    area: ['410px', '200px'],
                    yes: function () {
                        index = self.indexOf(pass);
                        passes.splice(index, 1);
                        pass.remove();
                        if (self.needSelect == true) {
                            self.selectSeat.numChange();
                        }
                        this.getPromotionList();
                        self.update();
                        layer.close(dialog);
                    },
                    no: function () {
                    }
                });
            } else {
                index = self.indexOf(pass);
                passes.splice(index, 1);
                pass.remove();
                if (self.needSelect == true) {
                    self.selectSeat.numChange();
                }
                this.getPromotionList();
                self.update();
            }
        }, indexOf: function (pass) {
            var passes = this.passengers;
            var index = -1;
            for (var i = passes.length - 1; i >= 0; i--) {
                if (passes[i] === pass) {
                    index = i;
                    break;
                }
            }
            return index;
        }, update: function () {
            var passenger = this.passengers;
            var index = 0;
            for (var i = 0; i < passenger.length; i++) {
                passenger[i].updateIndex(++index);
                var children = passenger[i].child;
                for (var h = 0; h < children.length; h++) {
                    children[h].updateIndex(++index);
                }
            }
            if (passengers.length === 1) {
                passengers[0].hideDelete();
            } else {
                passengers[0].showDelete();
            }
            this.restNum.text(this._total - passengers.length);
            if (this.fixed) {
                this.sideTip.css('top', this.summary.height());
            }
            this.calcPrice();
        }, get: function () {
            var ticketData = this.ticket.get();
            var insuranceInfo = this.insurance ? this.insurance.get() : {};
            var promotionInfo = this.promotion ? this.promotion.get() : {};
            var contactInfo = this.needSend ? {} : this.contact.get();
            var deliveryInfo = this.needSend ? this.sendTicket.get() : {};
            var deliveryType = this.needSend ? this.deliveryType : 0;
            var personalTailorInfo = this.needSelect ? this.selectSeat.get() : {};
            var passes = this.passengers;
            var passesInfo = [];
            var passInfo;
            var passInfoFlag = true;
            var acceptStandingTicket = false;
            for (var i = 0; i < passes.length; i++) {
                passInfo = passes[i].get();
                if (passInfo === false) {
                    passInfoFlag = false;
                } else {
                    passesInfo.push(passInfo);
                }
            }
            var seat = this.ticket.seat;
            if (seat == 9 || ((seat == 3 || seat == 8) && this.standingTicket[0].checked)) {
                acceptStandingTicket = true;
            }
            if (contactInfo === false || passInfoFlag === false || insuranceInfo === false) {
                return false;
            } else {
                return {
                    seatName: this.ticket.getSeatName(),
                    amount: passengers.length,
                    price: this.ticket.price,
                    tmpId: ticketData.tmpId,
                    ticketResId: ticketData.resId,
                    contactsInfo: contactInfo,
                    riderInfo: passesInfo,
                    acceptStandingTicket: acceptStandingTicket,
                    insuranceInfo: insuranceInfo,
                    promotionInfo: promotionInfo,
                    deliveryInfo: deliveryInfo,
                    deliveryType: deliveryType,
                    username12306: this.username12306,
                    password12306: this.password12306,
                    absId: this.absId,
                    isExcess: ticketData.isExcess,
                    personalTailorInfo: personalTailorInfo
                }
            }
        }, confirm: function (info, data) {
            var self = this;
            var payBtn;
            var confirmHTML;
            confirmHTML = orderConfirmRender(info);
            var dialog = $.layer({
                type: 1,
                guide: 2,
                title: '核对订单',
                closeBtn: [0, true],
                btns: 0,
                area: ['788px', 'auto'],
                border: [6, 1, '#c4c4c5'],
                page: {html: confirmHTML}
            });
            payBtn = $('#J_Pay');
            payBtn.click(function () {
                window.location.href = data.data.goto;
            });
            $("#xubox_layer" + dialog).find(".xubox_close").unbind("click").click(function () {
                var success_dialog = openSaveOrderDialog('<div class="dialog_loading"><div>' + data.msg + '</div><div class="dialog_btn"><a class="backto_book">取消订单</a><a href="' + data.data.goto + '" class="btn_ok goto_pay">继续付款</a></div></div>', true);
                $("#xubox_layer" + success_dialog).find(".backto_book").click(function () {
                    isSavingOrder = false;
                    $.post('/tn?r=train/TrainTicket/cancelOrder', {orderId: data.data.orderId}, function (data) {
                    }, 'json');
                    layer.close(success_dialog);
                    layer.close(dialog);
                });
            });
        }, checkOrder: function (info) {
            var self = this;
            if (self.isGettingInfo) {
                openSaveOrderDialog('<div class="dialog_loading">正在获取最新联系人信息,请稍候...</div>', true);
                return;
            }
            if (!isLogin) {
                self.openDialog();
                return;
            }
            if (isSavingOrder) {
                return;
            }
            self.check12306Acount(self, info);
        }, closeVerificationBox: function () {
            $('.verification-cover').hide();
            $('.verification-cover').remove();
            $('#showVerification').hide();
            $('#showVerification').remove();
        }, checkPassenger: function () {
            var self = this;
            if ($('#J_Send_title').hasClass('send_title_click')) {
                isPaperTicket = true;
            } else {
                isPaperTicket = false;
            }
            if ($('#J12306').css('display') != 'none') {
                _isBind12306Count = false;
            } else {
                _isBind12306Count = false;
            }
            if (isPaperTicket) {
                return true;
            } else if (!_isBind12306Count) {
                var adultPassengerList = $('.pass_info.passenger-adult[verificationstatus]');
                var verificationData, title, sendTicketStr;
                var statusList = adultPassengerList.map(function (v, ele) {
                    return $(ele).attr('verificationstatus') != '' ? $(ele).attr('verificationstatus') : 1;
                });
                sendTicketStr = "送票上门";
                statusList = $(statusList).get();
                if (statusList.indexOf("0") != -1 || statusList.indexOf("2") != -1) {
                    var nonameStr = $('.pass_info.passenger-adult[verificationstatus="0"]').find('.adult-put').map(function (x, ele) {
                        return $(ele).val()
                    });
                    var nameStr = $('.pass_info.passenger-adult[verificationstatus="2"]').find('.adult-put').map(function (x, ele) {
                        return $(ele).val()
                    });
                    nameStr = (nameStr.length == 0 ? "" : nameStr.get().join()) + (nonameStr.length == 0 ? "" : nonameStr.get().join());
                    verificatType = 0;
                    verificationData = {
                        "nameStr": nameStr,
                        "verificatType": verificatType,
                        "sendTicketStr": sendTicketStr,
                        "identityVerificationContinue": identityVerificationContinue
                    };
                } else if (statusList.indexOf("3") != -1) {
                    var nameStr = $('.pass_info.passenger-adult[verificationstatus="3"]').find('.adult-put').map(function (x, ele) {
                        return $(ele).val()
                    });
                    nameStr = nameStr.length == 0 ? "" : nameStr.get().join();
                    verificatType = 1;
                    verificationData = {
                        "nameStr": nameStr,
                        "verificatType": verificatType,
                        "sendTicketStr": sendTicketStr,
                        "identityVerificationContinue": identityVerificationContinue
                    };
                } else {
                    return true;
                }
                var showVerificationRender = template.compile($('#showVerificationTemplate').html());
                $('body').append(showVerificationRender(verificationData));
                $('.verification-cover').on('click', function () {
                    self.closeVerificationBox();
                });
                $('#sendTicket') && $('#sendTicket').on("click", function () {
                    if ($('#J_Send_title').length > 0) {
                        self.closeVerificationBox();
                        $('#J_Send_title').click();
                    } else {
                        window.location.href = location.origin;
                    }
                });
                $('#continueOrder') && $('#continueOrder').on("click", function () {
                    isPaperTicket = true;
                    self.closeVerificationBox();
                    $('#submitBtn').click();
                });
                $('#bind12306Btn') && $('#bind12306Btn').on("click", function () {
                    var J_bind12306 = $('#J_LoginBtn_12306');
                    J_bind12306.click();
                    self.closeVerificationBox();
                });
                $('#showVerification').show();
                return false;
            } else if (_isBind12306Count) {
                return true;
            }
        }, submitOrder: function (self, info) {
            var leftNumber = self.ticket.leftNumber;
            if (leftNumber < 15) {
                var dialog = openSaveOrderDialog('<div class="dialog_loading loading mb_20"><div class="pkg_error_con"><h3 class="secondCountInfo">正在验证是否可预订, 请稍等...</h3></div></div>');
                getLeftTicket(self, dialog, function () {
                    layer.close(dialog);
                    self.saveOrder(info);
                });
            } else {
                self.saveOrder(info);
            }
        }, check12306Acount: function (self, info) {
            $.post('/tn?r=train/TrainTicket/Check12306Account', {}, function (data) {
                if (data && data.success && data.data) {
                    if (2 == data.data.result) {
                        var dialog = $.layer({
                            guide: 2,
                            title: '提示',
                            type: 1,
                            page: {html: '<div class="dialog_tip">尊敬的客人，途牛正在拼命跟铁路12306联系，核对您的帐号是否正确。如您的12306帐号密码不匹配，或未通过手机验证，可能会导致您购票失败，途牛会很伤心的！</div>'},
                            border: [6, 1, '#c4c4c5'],
                            btns: 2,
                            btn: ['妥妥的，不会错', '我确认一下先'],
                            area: ['520px', '220px'],
                            yes: function () {
                                layer.close(dialog);
                                if (data.data.loginStatus == 1) {
                                    info.absId = data.data.absId;
                                } else {
                                    info.absId = 0;
                                }
                                self.submitOrder(self, info);
                            },
                            no: function () {
                                layer.close(dialog);
                            }
                        });
                    } else {
                        if (data.data.loginStatus == 1) {
                            info.absId = data.data.absId;
                        } else {
                            info.absId = 0;
                        }
                        self.submitOrder(self, info);
                    }
                } else {
                    info.absId = 0;
                    self.submitOrder(self, info);
                }
            }, 'json');
            $('.xubox_botton > .xubox_botton2').css('font-size', '14px');
        }, saveOrder: function (info) {
            var self = this;
            isSavingOrder = true;
            self.orderCountdown(info, true);
            $.post('/tn?r=train/TrainTicket/SaveOrder', info, function (data) {
                if (self.orderSeconds > 0) {
                    if (!data || (data && data.code != 200)) {
                        clearInterval(self.countDown_timeout);
                        layer.close(self.countDown_dialog);
                    }
                    if (!data) {
                        openSaveOrderDialog('<div class="dialog_loading"><i class="icon icon_wrong_b"></i>订单提交出错, 请重新提交</div>');
                        isSavingOrder = false;
                        return;
                    }
                    if (data.code == 200) {
                        self.orderId = data.data.orderId;
                        location.assign('/tn?r=user/user/trainOrderDetail&orderId=' + self.orderId + '&cFrom=book');
                    } else if (data.code == 502) {
                        var falid_dialog = openSaveOrderDialog('<div class="dialog_loading"><div class="pkg_error_con ml_-10"><h3><div class="msg">' + data.msg + '</div></h3></div><div class="dialog_btn"><a class="btn_ok">确定</a></div></div>');
                        $("#xubox_layer" + falid_dialog).find(".btn_ok").click(function () {
                            layer.close(falid_dialog);
                            returnTrainList(self);
                        });
                        setTimeout(function () {
                            returnTrainList(self);
                        }, 5000);
                        isSavingOrder = false;
                    } else if (data.code == 504) {
                        var falid_dialog = openSaveOrderDialog('<div class="dialog_loading chongdan"><div class="pkg_error_con ml_-10"><h3><div class="msg">' + data.msg + '</div></h3></div><div class="dialog_btn"><a class="btn_ok" href="' + data.data.goto + '">确定</a></div></div>');
                        isSavingOrder = false;
                        setTimeout(function () {
                            window.location.href = data.data.goto;
                        }, 5000);
                    } else {
                        var falid_dialog = openSaveOrderDialog('<div class="dialog_loading"><div class="pkg_error_con ml_-10"><h3><div class="msg">' + data.msg + '</div></h3></div><div class="dialog_btn"><a class="btn_ok">确定</a></div></div>');
                        $("#xubox_layer" + falid_dialog).find(".btn_ok").click(function () {
                            layer.close(falid_dialog);
                        });
                        isSavingOrder = false;
                    }
                }
            }, 'json');
        }, orderCountdown: function (info, flag) {
            var self = this;
            var seconds = self.orderSeconds = 35;
            var time = 100;
            var num = 0;
            self.countDown_dialog = openSaveOrderDialog('<div class="orderOccupy"><div style="width:100%;text-align: center;padding-top: 15px;"><img src="http://img4.tuniucdn.com/img/20150513/train/loading.gif" /></div>');
            $('.orderOccupy').parents('.xubox_page').css('width', '100%');
            $('.orderOccupy').parents('.xubox_page').siblings("#xubox_border1").css("background", "none");
            self.countDown_timeout = setInterval(function () {
                if (num > 9) {
                    num = 1;
                    var currentIndex = $(".orderOccupy .activeDot").index();
                    var index = (currentIndex + 1) % 3;
                    $(".orderOccupy .dot").eq(currentIndex).removeClass('activeDot');
                    $(".orderOccupy .dot").eq(index).addClass('activeDot');
                } else {
                    num++;
                }
                seconds = Math.ceil((seconds * 1000 - time * num) / 1000);
                seconds = seconds < 10 ? ('0' + seconds) : seconds;
                mseconds = Math.ceil((1 - Math.random()).toFixed(2) * 100);
                mseconds = mseconds < 10 ? ('0' + mseconds) : mseconds;
                $(".orderOccupy .second").html('00:' + seconds + '.');
                $(".orderOccupy .msecond").html(mseconds);
                if (seconds == 0) {
                    clearInterval(self.countDown_timeout);
                    if (flag && self.orderId) {
                        setTimeout(function () {
                            layer.close(self.countDown_dialog);
                            var dialog = openSaveOrderDialog('<div class="dialog_loading loading mb_20"><div class="msg pkg_loading_con">占位还没有结果，我们还在努力为您占座中，稍后会以短信提示占位结果，请关注短信提醒或订单状态变化</div><div class="dialog_btn"><a class="btn_wait mr_20">我要继续等待</a><a class="btn_ok"  href="http://www.tuniu.com/u/trainorder">我要查看订单</a></div></div>');
                            $("#xubox_layer" + dialog).find(".btn_wait").click(function () {
                                layer.close(dialog);
                                self.orderCountdown(info, false);
                                self.getOrderStaus(self.orderId, info);
                            });
                        }, 500);
                    } else {
                        setTimeout(function () {
                            layer.close(self.countDown_dialog);
                            openSaveOrderDialog('<div class="dialog_loading loading mb_20"><div class="msg pkg_loading_con">占位还没有结果，我们还在努力为您占座中，稍后会以短信提示占位结果，请关注短信提醒或订单状态变化</div><div class="dialog_btn"><a class="btn_ok" href="http://www.tuniu.com/u/trainorder">查看我的订单</a></div></div>');
                            setTimeout(function () {
                                window.location.href = "http://www.tuniu.com/u/trainorder";
                            }, 5000);
                        }, 500);
                    }
                }
            }, time);
        }, getOrderStaus: function (orderId, info) {
            var self = this;
            var _msg = "";
            $.post('/tn?r=train/TrainTicket/getOrderStatus', {'orderId': orderId}, function (data) {
                if (self.orderSeconds > 0) {
                    if (!data || (data && data.code != 202)) {
                        clearInterval(self.countDown_timeout);
                        layer.close(self.countDown_dialog);
                    }
                    if (!data) {
                        openSaveOrderDialog('<div class="dialog_loading loading mb_20"><div class="msg pkg_loading_con">占位还没有结果，我们还在努力为您占座ing，稍后会以短信提示占位结果，请关注短信提醒或订单状态变化</div><div class="dialog_btn"><a class="btn_ok" href="http://www.tuniu.com/u/trainorder">查看我的订单</a></div></div>');
                        isSavingOrder = false;
                        return;
                    }
                    if (data.code == 200) {
                        var isAdvancePay = data.data.orderDetail ? data.data.orderDetail.isAdvancePay : 0;
                        if (isAdvancePay == 1) {
                            $.post('/tn?r=train/trainTicket/getNewPayInfo', {'orderId': orderId}, function (res) {
                                if (res.success) {
                                    window.open(res.data.url, '_self');
                                } else {
                                    location.assign('/tn?r=user/user/trainOrderDetail&orderId=' + orderId);
                                }
                            }, 'json');
                        } else {
                            location.assign('/tn?r=user/user/trainOrderDetail&orderId=' + orderId);
                        }
                    } else if (data.code == 202) {
                        openSaveOrderDialog('<div class="dialog_loading loading mb_20"><div class="msg pkg_loading_con">' + data.msg + '</div><div class="dialog_btn"><a class="btn_ok" href="http://www.tuniu.com/u/trainorder">查看我的订单</a></div></div>');
                        isSavingOrder = false;
                        return;
                    } else {
                        if (data.msg == "s12306-1") {
                            _msg = "因铁路总局核验系统升级，为防止乘客身份信息被冒用，请您登录12306账号购买";
                            msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class="errorcontent"><h3><div class="msg">' + _msg + '</div></h3></div><div class="dialog_btn dialog_btn2"><a class="btn_ok2">登录12306购票</a> <a class="btn_ok1">取消购票</a>  </div></div>';
                            var falid_dialog = $.layer({
                                type: 1,
                                guide: 2,
                                title: false,
                                btns: 0,
                                area: ['470px', 'auto'],
                                border: [6, 1, '#c4c4c5'],
                                page: {html: msg}
                            });
                            $("#xubox_layer" + falid_dialog).find(".btn_ok2").click(function () {
                                layer.close(falid_dialog);
                                var dialog = self.openlogin12306();
                                $("#xubox_layer" + dialog).find(".btn_ok2").click(function () {
                                    layer.close(dialog);
                                });
                            }).end().find(".btn_ok1").click(function () {
                                returnTrainList(self);
                            });
                        } else if (data.msg == "s12306-2") {
                            _msg = "登录名不存在";
                            var dialog = self.openlogin12306();
                            $("#login_error").html(_msg).show();
                            $("#xubox_layer" + dialog).find(".btn_ok2").click(function () {
                                layer.close(dialog);
                            });
                        } else if (data.msg == "s12306-3") {
                            _msg = "该用户已被暂停使用，如有疑问请拨打铁路客户服务电话010-12306";
                            msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class="errorcontent"><h3><div class="msg">' + _msg + '</div></h3></div><div class="dialog_btn dialog_btn2"><a class="btn_ok2">确定</a>   </div></div>';
                            var falid_dialog = $.layer({
                                type: 1,
                                guide: 2,
                                title: false,
                                btns: 0,
                                area: ['470px', 'auto'],
                                border: [6, 1, '#c4c4c5'],
                                page: {html: msg}
                            });
                            $("#xubox_layer" + falid_dialog).find(".btn_ok2").click(function () {
                                layer.close(falid_dialog);
                            })
                        } else if (data.msg == "s12306-4") {
                            _msg = "密码输入错误。如果输错次数超过4次，用户将被锁定";
                            self.openlogin12306();
                            $("#login_error").html(_msg).show();
                            $("#xubox_layer" + falid_dialog).find(".btn_ok2").click(function () {
                                layer.close(falid_dialog);
                            });
                        } else if (data.msg == "s12306-5") {
                            _msg = "您的用户已经被锁定,锁定时间为20分钟,请稍后再试";
                            msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class="errorcontent"><h3><div class="msg">' + _msg + '</div></h3></div><div class="dialog_btn dialog_btn2"><a class="btn_ok2">确定</a>   </div></div>';
                            var falid_dialog = $.layer({
                                type: 1,
                                guide: 2,
                                title: false,
                                btns: 0,
                                area: ['470px', 'auto'],
                                border: [6, 1, '#c4c4c5'],
                                page: {html: msg}
                            });
                            $("#xubox_layer" + falid_dialog).find(".btn_ok2").click(function () {
                                layer.close(falid_dialog);
                            })
                        } else {
                            _msg = data.msg;
                            msg = '<div class="dialog_loading"><div class="errortitle">提示</div><div class="errorcontent"><h3><div class="msg">' + _msg + '</div></h3></div><div class="dialog_btn dialog_btn2"><a class="btn_ok2">确定</a>   </div></div>';
                            var falid_dialog = $.layer({
                                type: 1,
                                guide: 2,
                                title: false,
                                btns: 0,
                                area: ['430px', 'auto'],
                                border: [6, 1, '#c4c4c5'],
                                page: {html: msg}
                            });
                            $("#xubox_layer" + falid_dialog).find(".btn_ok2").click(function () {
                                layer.close(falid_dialog);
                            })
                        }
                        self.absId = 0;
                        isSavingOrder = false;
                    }
                }
            }, 'json');
        }, calcPrice: function () {
            var self = this;
            var pass;
            var passes = passengers;
            var adultNum = 0;
            var childNum = 0;
            var price = this.ticket.price;
            var seat = this.ticket.seat;
            var totalPrice = 0;
            var reducePrice = 0;
            var insurancePrice = self.insurancePrice;
            var promotionPrice = self.promotionPrice || 0;
            var html = '';
            for (var i = 0; i < passes.length; i++) {
                pass = passes[i];
                if (pass._type === 'adult') {
                    adultNum++;
                } else {
                    childNum++;
                }
            }
            var personCount = adultNum + childNum;
            var chargeSend = self.needSend ? (2 == self.deliveryType ? self.sendFee : deliveryFee_) : 0;
            var purchasingFee = self.needSend ? self.purchasingFee : 0;
            var totalSendTicketPrice = self.needSend ? (personCount * purchasingFee + chargeSend) : 0;
            totalPrice = adultNum * price + childNum * price - promotionPrice + totalSendTicketPrice;
            html += '<dd class="mt5"><span class="number">车票价格</span><span class="price"></span> <span class="amount">￥' + price + 'x' + personCount + '</span></dd>';
            if (self.buyInsuranceFlag) {
                html += '<dd class="mt5"><span class="number">保险价格</span><span class="price"></span> <span class="amount">￥' + insurancePrice + 'x' + personCount + '</span></dd>';
                totalPrice += parseInt(adultNum + childNum, 10) * insurancePrice;
            }
            if (self.needSend) {
                html += '<dd class="mt5" id="J_ChargeSend"><span class="number">铁路客票代收费</span> <span class="price"></span> <span class="amount">￥' + purchasingFee + 'x' + personCount + '</span></dd>';
                html += '<dd class="mt5" id="J_SendPrice"><span class="number">配送费</span> <span class="price"></span> <span class="amount">￥' + chargeSend + '</span></dd>';
            }
            html += '<dd class="mt5 hide" id="J_ReducePrice"><span class="number">优惠金额</span> <span class="price"></span> <span class="amount">￥0</span></dd>';
            $('#J_PriceInfo').html(html);
            $('#J_TotalPrice').text(totalPrice > 0 ? totalPrice : 0);
            if (this.pricePlans) {
                var pricePlan;
                var tempPlan;
                $.each(this.pricePlans, function (i, item) {
                    if (item.seatType == seat) {
                        self.ticket.leftNumber = item.leftTicket;
                    }
                    $.each(item.pricePlans, function (j, obj) {
                        if (item.seatType == seat && obj.adultCount == (adultNum + childNum)) {
                            pricePlan = obj;
                        }
                    });
                });
                $.each(this.pricePlans, function (i, item) {
                    $.each(item.pricePlans, function (j, obj) {
                        if (item.seatType == seat && obj.adultCount == 3) {
                            tempPlan = obj;
                        }
                    });
                });
                if (pricePlan) {
                    this.pricePlan = pricePlan;
                    $('#J_TotalPrice').text(pricePlan.totalPrice + (self.needSend ? totalSendTicketPrice : 0) + (self.buyInsuranceFlag ? (parseInt(adultNum + childNum, 10) * insurancePrice) : 0) - promotionPrice);
                    if (pricePlan.totalReducePrice) {
                        var totalPromotionPrice = pricePlan.totalReducePrice + reducePrice - promotionPrice;
                        $('#J_ReducePrice .amount').text('-￥' + totalPromotionPrice);
                        $('#J_ReducePrice').show();
                    } else {
                        $('#J_ReducePrice .amount').text('￥0');
                        $('#J_ReducePrice').hide();
                    }
                    $("#J_Pass .J_PriceTip").show();
                    if (tempPlan) {
                        if (!tempPlan.hasText || (tempPlan.hasText && tempPlan.textContent.indexOf("抹零") > 0)) {
                            $("#J_Pass .J_PriceTip").hide();
                        }
                    } else {
                        $("#J_Pass .J_PriceTip").hide();
                    }
                } else {
                    this.noPricePlanUpdate(promotionPrice);
                }
            } else {
                this.noPricePlanUpdate(promotionPrice);
            }
            var currentOption = $('#J_TrainType').find('option:checked').text(),
                isSleepSeat = /铺|卧/g.test(currentOption);
            if (isSleepSeat) {
                var currentHours = new Date().getHours();
                if (7 <= currentHours && currentHours < 23) {
                    $('.J-sleepPriceInfo').text('页面卧铺票价仅供参考，请以出票时的实际价格为准');
                }
                $('.J-sleepPriceInfo').show();
            } else {
                $('.J-sleepPriceInfo').hide();
            }
        }, noPricePlanUpdate: function (reducePrice) {
            $("#J_Pass .J_PriceTip").hide();
            if (reducePrice > 0) {
                $('#J_ReducePrice .amount').text('-￥' + reducePrice);
                $('#J_ReducePrice').show();
            } else {
                $('#J_ReducePrice .amount').text('￥0');
                $('#J_ReducePrice').hide();
            }
        }, openDialog: function () {
            var ssoLoginFlag = document.getElementById('ssoLoginFlag');
            if ((typeof (ssoLoginFlag) == "undefined" || ssoLoginFlag == null) || !($.layer && typeof ($.layer) == "function")) {
                var self = this;
                var loginDialogHtml = $('#T_LoginDialog').html();
                var usernameEle, passwordEle, codeEle, errorEle, loadingEle, codeImg, codeBtn, submitBtn;
                var dialog = $.layer({
                    type: 1,
                    guide: 2,
                    title: '登录',
                    closeBtn: [0, true],
                    btns: 0,
                    area: ['350px', 'auto'],
                    border: [6, 1, '#c4c4c5'],
                    page: {html: loginDialogHtml},
                    success: function (wrap) {
                        wrap = $(wrap);
                        usernameEle = wrap.find('.J_LoginName');
                        passwordEle = wrap.find('.J_LoginPassword');
                        codeEle = wrap.find('.J_LoginCode');
                        errorEle = wrap.find('.J_LoginError');
                        loadingEle = wrap.find('.J_LoginLoading');
                        codeImg = wrap.find('.J_LoginCodeImg');
                        codeBtn = wrap.find('.J_LoginCodeBtn');
                        submitBtn = wrap.find('.J_LoginBtn');
                        codeBtn.click(changeCode);
                        submitBtn.click(beforeSubmit);
                    }
                });
                $(".xubox_close").on("click", function () {
                    layer.close(dialog);
                })

                function beforeSubmit() {
                    var username = $.trim(usernameEle.val());
                    var password = passwordEle.val();
                    var code = $.trim(codeEle.val());
                    if (!username) {
                        showError('用户名不能为空!');
                        return;
                    }
                    if (!password) {
                        showError('密码不能为空');
                        return;
                    }
                    if (!code) {
                        showError('验证码不能为空');
                        return;
                    }
                    self.login(username, password, code, afterLogin);
                }

                function showError(msg) {
                    if (msg) {
                        errorEle.text(msg).show();
                    } else {
                        errorEle.text('').hide();
                    }
                }

                function changeCode() {
                    var src = '/identify.php?flag=100&cache=';
                    codeImg.attr('src', src + _.now());
                }

                function afterLogin(data) {
                    if (data && !data.success) {
                        loadingEle.hide();
                        var errorMsg = '您输入的账号或密码或验证码不正确';
                        if (undefined != data.msg) {
                            errorMsg = data.msg;
                        }
                        showError(errorMsg);
                        submitBtn[0].disabled = false;
                        changeCode();
                        return false;
                    } else {
                        showError('');
                        layer.close(dialog);
                        isLogin = true;
                        self.getInfo();
                    }
                }
            } else {
                var self = this;
                var str = window.location.host;
                var domain = str.split(".")[0];
                var iframeSrc = 'https://passport.tuniu.com/login/iframe?origin=' + encodeURIComponent('http://www.tuniu.com/ssoConnect/Iframe?reload=trainorder&domain=' + domain);
                if (window.TrainAfterLogin == null || window.TrainAfterLogin == undefined || !window.TrainAfterLogin) {
                    window.TrainAfterLogin = function () {
                        isLogin = true;
                        self.afterLogin();
                        self.getInfo();
                        return;
                    };
                }
                var url = '/tn?r=ssoConnect/checkloginStatus';
                $.get(url, function (data) {
                    if (data == 0) {
                        self.dialog = $.layer({
                            type: 2,
                            title: false,
                            shadeClose: true,
                            closeBtn: [1, true],
                            iframe: {src: iframeSrc},
                            area: ['375px', '350px']
                        });
                    } else {
                        self.afterLogin();
                        isLogin = true;
                        self.getInfo();
                        return;
                    }
                });
            }
        }, openlogin12306: function () {
            var self = this;
            var isCheckCodeForWeb = $("#isCheckCodeForWeb").val();
            if (1 == isCheckCodeForWeb) {
                var url = '/tn?r=train/trainTicket/Get12306Encode';
                $.get(url, function (data) {
                    if (data.errorCode == 200) {
                        var imgID = $('#imageID');
                        for (var outer in window.tapArr) {
                            delete window.tapArr[outer];
                        }
                        imgID.attr('src', data.data);
                    }
                }, 'json');
            }
            var loginDialogHtml = $('#T_LoginDialog12306').html();
            var usernameEle, passwordEle, codeEle, errorEle, loadingEle, codeImg, codeBtn, submitBtn;
            $('body').css('overflow', 'hidden');
            $('body').scrollTop(0);
            var dialog = $.layer({
                type: 1,
                guide: 2,
                title: false,
                closeBtn: [0, true],
                btns: 0,
                area: ['375px', 'auto'],
                border: [0, 1, '#c4c4c5'],
                page: {html: loginDialogHtml},
                success: function (wrap) {
                    wrap = $(wrap);
                    usernameEle = wrap.find('.username12306');
                    passwordEle = wrap.find('.password12306');
                    errorEle = wrap.find('.login_error');
                    submitBtn = wrap.find('.sub');
                    loadingEle = wrap.find(".loading");
                    submitBtn.click(beforeSubmit);
                    $('#J_refresh').click(refreshEncode);
                    $('#J_showPass').click(function () {
                        if ($(this).hasClass('passshow')) {
                            document.getElementById("J_12306pass").type = "password";
                            $(this).removeClass('passshow');
                        } else {
                            document.getElementById("J_12306pass").type = "text";
                            $(this).addClass('passshow');
                        }
                    });
                }
            });
            $(".xubox_close").click(function () {
                layer.close(dialog);
                $('body').css('overflow', '');
            });
            usernameEle.on("focus", function () {
                $("#line_1").removeClass("err");
                $(this).parent().removeClass("err");
            });
            passwordEle.on("focus", function () {
                $("#line_2").removeClass("err");
                $(this).parent().removeClass("err");
            });

            function beforeSubmit() {
                var username = $.trim(usernameEle.val());
                var password = passwordEle.val();
                var randCode = $('[name="randCode"]').val();
                var e = /^(13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9])\d{8}$/;
                var d = /^[A-Za-z]{1}([A-Za-z0-9]|[_]){0,29}$/;
                var f = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
                if (!d.test(username) && !f.test(username) && !e.test(username)) {
                    showError('用户名格式不正确！');
                    $("#line_1").addClass("err");
                    usernameEle.parent().addClass("err");
                }
                if (!username) {
                    showError('用户名不能为空!');
                    $("#line_1").addClass("err");
                    usernameEle.parent().addClass("err");
                    return;
                }
                if (!password) {
                    showError('密码不能为空');
                    $("#line_2").addClass("err");
                    passwordEle.addClass("err");
                    return;
                }
                if (password.length < 6) {
                    showError('密码不能少于6位!');
                    $("#line_2").addClass("err");
                    passwordEle.addClass("err");
                    return;
                }
                loadingEle.show();
                self.login12306(username, password, randCode, afterLogin);
            }

            function showError(msg) {
                if (msg) {
                    errorEle.html(msg).show();
                } else {
                    errorEle.html('').hide();
                }
            }

            function afterLogin(data) {
                loadingEle.hide();
                if (data) {
                    if (!data.success) {
                        showError(data.msg);
                        submitBtn[0].disabled = false;
                        if (1 == $("#isCheckCodeForWeb").val()) {
                            var url = '/tn?r=train/trainTicket/Get12306Encode';
                            $.get(url, function (returndata) {
                                if (returndata.errorCode == 200) {
                                    var imgID = $('#imageID');
                                    imgID.attr('src', returndata.data);
                                }
                            }, 'json');
                            $("#randCode").val("");
                            for (var outer in window.tapArr) {
                                delete window.tapArr[outer];
                            }
                            $(".tapArea").remove();
                        }
                        return false;
                    } else {
                        $(".shade_note").show();
                        $("#J_bindSuccess12306").show();
                        self.username12306 = $.trim(usernameEle.val());
                        self.password12306 = passwordEle.val();
                        showError('');
                        layer.close(dialog);
                        $('body').css('overflow', '');
                        if (isLogin) {
                            $("#J_LoginBtn").hide();
                        }
                        self.absId = data.data.absId;
                        $("#J12306").hide();
                        $("#J_username12306").html(self.username12306);
                        $("#J12306ed").show();
                        setTimeout(function () {
                            $(".shade_note").hide();
                            $("#J_bindSuccess12306").hide();
                        }, 3000);
                    }
                }
            }

            function refreshEncode() {
                var url = '/tn?r=train/trainTicket/Get12306Encode';
                $.get(url, function (returndata) {
                    if (returndata.errorCode == 200) {
                        var imgID = $('#imageID');
                        imgID.attr('src', returndata.data);
                    }
                }, 'json');
                $("#randCode").val("");
                for (var outer in window.tapArr) {
                    delete window.tapArr[outer];
                }
                $(".tapArea").remove();
            }

            return dialog;
        }, login: function (username, pass, code, callback) {
            var self = this;
            var url, param;
            if (isLogin) {
                callback({success: true});
            } else {
                url = '/main.php?do=order_login_ajax&flag=login&t=' + Math.random();
                param = {'username': username, 'password': pass, 'identify': code, 'needReturn': 1}
                $.post(url, param, function (json) {
                    if (json.success) {
                        isLogin = true;
                        self.afterLogin();
                    }
                    callback(json);
                }, 'json');
            }
        }, login12306: function (username, pass, randCode, callback) {
            var self = this;
            var url, param;
            var isCheckCodeForWeb = $("#isCheckCodeForWeb").val();
            if (0 == isCheckCodeForWeb) {
                randCode = 0;
            }
            url = '/tn?r=train/TrainTicket/Login12306';
            param = {
                'userName': username,
                'passWord': pass,
                'randCode': randCode,
                'absId': self.absId,
                'isCheckCodeForWeb': isCheckCodeForWeb
            };
            $.post(url, param, function (json) {
                if (json && (json.errorCode == 200)) {
                    callback({success: true, data: json.data});
                } else {
                    callback(json);
                }
            }, 'json');
        }, logout12306: function () {
            var self = this;
            var url, param;
            url = '/tn?r=train/TrainTicket/Logout12306';
            param = {'absId': self.absId};
            $.post(url, param, function (json) {
                if (json.success) {
                    $("#J12306").show();
                    $("#J12306ed").hide();
                } else {
                    $("#J12306").hide();
                    $("#J12306ed").show();
                }
            }, 'json');
        }, afterLogin: function () {
            $("#J_LoginBtn").hide();
            this.contact.hideMobileLoginTip();
            if (isLogin) {
                this.getPromotionList();
                this.calcPrice();
            }
        }, getPromotionList: function () {
            var self = this;
            var pass;
            var passes = passengers;
            var adultCount = 0;
            var childCount = 0;
            var adultPrice = this.ticket.price;
            var productPrice = 0;
            var departureCityCode = this.ticket.data.startStationCode;
            var departsDate = this.ticket.data.startDate;
            for (var i = 0; i < passes.length; i++) {
                pass = passes[i];
                if (pass._type === 'adult') {
                    adultCount++;
                } else {
                    childCount++;
                }
            }
            productPrice = adultCount * adultPrice + childCount * adultPrice;
            var param = {
                'adultCount': adultCount,
                'adultPrice': adultPrice,
                'childCount': childCount,
                'productPrice': productPrice,
                'departureCityCode': departureCityCode,
                'departsDate': departsDate
            };
            $.get('/tn?r=train/trainTicket/GetPromotionList', param, function (data) {
                if (data.code == 200) {
                    self.promotion = new Promotion(data.promotionList, this);
                    self.promotion.bind("change", function () {
                        var selectedPromotion = self.promotion.get();
                        self.promotionPrice = selectedPromotion ? selectedPromotion.price : 0;
                        self.calcPrice();
                    });
                    self.promotion.radios[0].checked = true;
                    self.promotion.trigger("change");
                }
            }, 'json');
        }, getInfo: function () {
            var self = this;
            self.isGettingInfo = true;
            $.get('/tn?r=train/TrainTicket/GetContactsInfo', function (data) {
                if (data.code == 200) {
                    self.contact.set(data.data.userInfo);
                    self.contacts.load(data.data.contactsInfo);
                    self.isGettingInfo = false;
                    userId = data.data.userInfo.userId;
                }
            }, 'json');
        }, checkSameCode: function () {
            var passengers = this.passengers;
            var passenger;
            var nType, nCode;
            var res = {};
            var key;
            for (var i = 0; i < passengers.length; i++) {
                passenger = passengers[i];
                nType = passenger.idType.val();
                nCode = $.trim(passenger.idCode.val());
                key = nType + '-' + nCode;
                res[key] = res[key] || [];
                res[key].push(passenger);
            }
            for (var n in res) {
                if (res.hasOwnProperty(n)) {
                    if (res[n].length >= 2) {
                        for (var h = 0; h < res[n].length; h++) {
                            res[n][h].addError('idCode', 4);
                            res[n][h].wrap.find('.J_TipIdType').hide();
                        }
                    } else {
                        res[n][0].removeError('idCode', 4);
                    }
                }
            }
        }
    }, Events);
    var order = new Order();

    function openSaveOrderDialog(msg, isShow, callback) {
        var closeOption;
        if (isShow) {
            closeOption = [0, true];
        } else {
            closeOption = false;
        }
        callback = callback || function () {
        };
        return $.layer({
            type: 1,
            guide: 2,
            title: false,
            closeBtn: closeOption,
            btns: 0,
            close: callback,
            area: ['470px', 'auto'],
            border: [6, 1, '#c4c4c5'],
            page: {html: msg}
        });
    }

    function getPassIndex(pass) {
        for (var i = 0; i < passengers.length; i++) {
            if (passengers[i] === pass) {
                return i;
            }
        }
        return -1;
    }

    function uniformDateFormat(date) {
        if (typeof date === 'string') {
            return date.replace(/\-/g, '/');
        }
        return date;
    }

    function str2Date(date) {
        if (!date) {
            return null;
        }
        if (typeof date === 'string') {
            date = uniformDateFormat(date);
            date = new Date(date);
        } else {
            date = new Date(+date);
        }
        return date;
    }

    function isAdult(birth) {
        birth = str2Date(birth);
        birth.setFullYear(birth.getFullYear() + 18);
        return +birth < +today;
    }

    function getLeftTicket(order, dialog, callback) {
        var orderData = order.ticket.data;
        var params = {
            'trainNumber': orderData.trainNum,
            'startStationName': orderData.departStationName,
            'arriveStationName': orderData.destStationName,
            'startDate': orderData.startDate,
            'seatTypes': [order.ticket.seat]
        }
        $.get('/tn?r=train/trainTicket/getPricePlan', {"param": params}, function (tempData) {
            if (tempData.code == 200) {
                var tempData = tempData.data[0];
                var leftNumber = tempData.leftTicket;
                if (leftNumber > 0 && leftNumber >= passengers.length) {
                    callback && callback(tempData);
                } else {
                    if (dialog) {
                        layer.close(dialog);
                    }
                    var msg = '';
                    if (leftNumber <= 0) {
                        msg = '对不起，您所选的车次/坐席已无票，请选择其他车次/坐席';
                    } else {
                        msg = '由于同时下单人数过多，您所选的车次/坐席余票不足，请选择其他车次/坐席';
                    }
                    var falid_dialog = openSaveOrderDialog('<div class="dialog_loading"><div class="pkg_error_con ml_-10"><h3><div class="msg">' + msg + '</div></h3></div><div class="dialog_btn"><a class="btn_ok">确定</a></div></div>');
                    $("#xubox_layer" + falid_dialog).find(".btn_ok").click(function () {
                        layer.close(falid_dialog);
                        returnTrainList(order);
                    });
                    setTimeout(function () {
                        returnTrainList(order);
                    }, 5000);
                }
            } else {
                callback && callback();
            }
        }, 'json');
    }

    function returnTrainList(order) {
        var orderData = order.ticket.data;
        window.location.href = "/station_" + orderData.startStationCode + "_" + orderData.desStationCode + "?date=" + orderData.startDate;
    }
})();