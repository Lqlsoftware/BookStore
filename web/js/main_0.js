var pageNo = 1;
var pageSize = 9;
var pageTotal;
var isLast;
var isSearch = 0;
var bookStorge;
var cartStorge;

$(document).ready(function () {
    $('.navSearch').append('<input type="text" oninput="searchPageList();">');
    $('.navSearch input').attr("onfocus", "$('.cover').css({'background': 'rgba(48,52,64,0.8)'});$('.cover').empty();$('.cover').show();");
    $('.navSearch input').attr("onblur", "$('.cover').css({'background': 'rgba(48,52,64,1)'});$('.cover').hide();");
    $('.search #from').attr("oninput", "getSearchPageList();");
    $('.search #to').attr("oninput", "getSearchPageList();");
    $('.mycart').append('<img class="cartImg" src="img/cart.png"><p class="checkCart" ></p>');
    getCart();
    getPageList();
    $('.searchbtn').click(function () {
        getSearchPageList();
    });
    $('.cover').hide();
    $('.container').append('<div class="billsbtn" onclick="getBillsInfo();"><p>账单</p></div>');
    $('.mycart').on("mouseenter", function () {
        $('.mycart').css({
            "background-color": "#057549"
        })
    })
    $('.mycart').on("mouseleave", function () {
        $('.mycart').css({
            "background-color": "#0bd38a"
        })
    })
    $('.mycart').click(function () {
        getCartInfo();
    })
    $('.logo').click(function () {
        $('.cover').hide();
    })
    $('.logo').on("mouseenter", function () {
        $('.logo').css({
            "background-color": "#057549"
        })
    })
    $('.logo').on("mouseleave", function () {
        $('.logo').css({
            "background-color": "#0bd38a"
        })
    })
    $('.billsbtn').on("mouseenter", function () {
        $('.billsbtn').css({
            "background-color": "#057549"
        })
    })
    $('.billsbtn').on("mouseleave", function () {
        $('.billsbtn').css({
            "background-color": "#0bd38a"
        })
    })
});

function getBillsInfo() {
    $.ajax({
        type: "GET",
        url: 'getBills',
        success: function (res) {
            if (res.code === 1) {
                $('.cover').empty();
                var user = $('<div class="bill_user">用户: ' + res.data.user + '</div>');
                $('.cover').append(user);
                var bill = $('<div class="bill"></div>');
                $.each(res.data.trade, function (index, context) {
                    var trade = $('<div class="trade"></div>');
                    var trade_time = $('<div class="bill_time">交易时间: ' + getLocalTime(context.trade_time, 1) + '</div>');
                    var trade_body = $('<div class="trade_body"></div>');
                    var trade_head = $('<div><div class="headName">书名</div>' +
                        '<div class="Price">单价</div>' +
                        '<div class="headNum">数量</div></div>');
                    trade_body.append(trade_head);
                    $.each(context.collection, function (index, info) {
                        trade_body.append('<div><div class="itemName">' + info.title + '</div>' +
                            '<div class="Price"> ¥ ' + info.price + '</div>' +
                            '<div class="itemNum">' + info.quantity + '</div></div>')
                    })
                    trade.append(trade_time);
                    trade.append(trade_body);
                    bill.append(trade);
                })
                var trade_footer = $( '<div class="footer"><button onclick="$(\'.cover\').hide();">返回</button></div>');
                $('.cover').append(bill);
                $('.cover').append(trade_footer);
                $('.cover').show();
            }
        },
        dataType: "json"
    })
}

function procCart(res, code) {
    if (res.code === 1) {
        cartStorge = res.data;
        var tip = $('<div class="tip"></div>');
        if ($('.tip') !== undefined)
            $('.tip').remove();
        if (res.errMsg !== "") {
            tip.append('<div class="tip_u"></div><div class="tip_d"></div>' +
                '<div class="cartMsg">' + res.errMsg + '</div>');
            $('body').append(tip);
            $('.tip_d').css({
                "top": $('.tip_u').height() + $('.cartMsg').height()
            })
            tip.fadeOut(2000, function () {
                tip.remove();
            });
        }
        $('.checkCart').empty();
        $('.checkCart').append(res.data.num);
        if (code === 1)
            getCartInfo();
        else if (code === 2) {
            $('.total').empty();
            $('.total').append(res.data.num);
        }
    }
}

function getCart() {
    $.ajax({
        type: "GET",
        url: 'getCart',
        success: function (res) {
            procCart(res, 0);
        },
        dataType: "json"
    })
}

function addToCart(id, num) {
    $.ajax({
        type: "GET",
        url: 'addBooksToCart?id=' + id + '&quantity=' + num,
        success: function (res) {
            procCart(res, 2);
        },
        dataType: "json"
    })
}


function setCart(id, num) {
    $.ajax({
        type: "GET",
        url: 'setCart?id=' + id + '&quantity=' + num,
        success: function (res) {
            procCart(res, 1);
        },
        dataType: "json"
    })
}

function clearCart() {
    $.ajax({
        type: "GET",
        url: 'clearCart',
        success: function (res) {
            procCart(res, 1);
        },
        dataType: "json"
    })
}


function getCartInfo() {
    $('.cover').empty();
    var detail = $('<div class="cart_detail"></div>');
    var header = $('<div class="cart_header"><div class="headName">书名</div>' +
        '<div class="headNum">数量</div>' +
        '<div class="headPrice">价格</div></div>');
    var body = $('<div class="cart_body"></div>')
    var footer = $( '<div class="footer">' +
        '<div class="footer_total">总金额: ¥' + cartStorge.total + '</div>' +
        '<button onclick="getPay();">结账</button>' +
        ' <button onclick="clearCart();">清空购物车</button>' +
        ' <button onclick="$(\'.cover\').hide();">返回</button></div>')

    $.each(cartStorge.queryList, function(index, content) {
        var cartItem = $('<div class="cartItem"></div>');
        cartItem.append(
            '<div class="itemName">' + content.book.title + '</div>' +
            '<div class="itemNum"><img onclick="setCart(' + content.book.id + ',' + (content.quantity - 1) + ');" src="img/sub.png">' +
            '<input onchange="setCart(' + content.book.id + ',this.value);" type="number" value="' + content.quantity + '">' +
            '<img onclick="setCart(' + content.book.id + ',' + (content.quantity + 1) + ');" src="img/add.png"></div>' +
            '<div class="itemPrice">¥ ' + content.totalMoney +
            ' <button class="del" onclick="setCart(' + content.book.id + ',0);">删除</button></div>'
        );
        body.append(cartItem);
    });
    detail.append(header);
    detail.append(body);
    detail.append(footer);
    $('.cover').append(detail);
    $('.cover').show();
}

function pay() {
    $.ajax({
        type: "POST",
        url: 'pay',
        data: {
            'name': $('.pay_userName input').val(),
            'cardId': $('.pay_cardId input').val()
        },
        success: function (res) {
            if (res.code === -1) {
                $('.pay_error').empty();
                $('.pay_error').append(res.errMsg);
            } else if (res.code === 1) {
                $('.cover').empty();
                var trade = $('<div class="trade"></div>');
                var trade_title = $('<div class="trade_title"><h1>账单</h1></div>');
                var trade_user = $('<div class="trade_user">用户: ' + res.data.user + '</div>');
                var trade_detail = $('<div class="trade_time">交易时间: ' + getLocalTime(res.data.trade_time, 1) + '</div>' +
                    '<div class="trade_money">总计 ¥ ' + res.data.trade_total + '</div>');
                var trade_body = $('<div class="trade_body"></div>');
                var trade_head = $('<div><div class="headName">书名</div>' +
                    '<div class="Price">单价</div>' +
                    '<div class="headNum">数量</div></div>');
                trade_body.append(trade_head);
                $.each(res.data.queryList, function (index, context) {
                    trade_body.append('<div><div class="itemName">' + context.book.title + '</div>' +
                        '<div class="Price"> ¥ ' + context.book.price + '</div>' +
                        '<div class="itemNum">' + context.quantity + '</div></div>')
                })
                var trade_footer = $( '<div class="footer"><button onclick="$(\'.cover\').hide();getCart();">返回</button></div>');

                trade.append(trade_title);
                trade.append(trade_user);
                trade.append(trade_detail);
                trade.append(trade_body);
                trade.append(trade_footer);
                $('.cover').append(trade);
                $('.cover').show();
                getCart();
            }
        },
        dataType: "json"
    })
}


function getPay() {
    $('.cover').empty();
    var pay = $('<div class="pay"></div>');
    var pay_title = $('<div class="pay_title"><h1>收银台</h1></div>');
    var pay_total = $('<div class="pay_detail">购物车中总计 ' + cartStorge.num + ' 本书</div>');
    var pay_money = $('<div class="pay_detail">应付 ¥ ' + cartStorge.total + '</div>');
    var pay_error = $('<div class="pay_error"></div>');
    var pay_userName = $('<div class="pay_userName">信用卡姓名: ' + '<input type="text"></div>');
    var pay_cardId = $('<div class="pay_cardId">信用卡账号: ' + '<input type="text"></div>');
    var pay_footer = $( '<div class="footer"><button onclick="pay();">确认</button> <button onclick="$(\'.cover\').hide();">返回</button></div>');

    pay.append(pay_title);
    pay.append(pay_total);
    pay.append(pay_money);
    pay.append(pay_error);
    pay.append(pay_userName);
    pay.append(pay_cardId);
    pay.append(pay_footer);
    $('.cover').append(pay);
    $('.cover').show();
}



function Page(target) {
    pageNo = target;
    if (isSearch === 1)
        getSearchPageList();
    else if (isSearch === 2)
        searchPageList();
    else
        getPageList();
}

function procPage(res) {
    if (res.code === 1) {
        bookStorge = res.data.queryList;
        pageNo = res.data.pageNo;
        pageSize = res.data.pageSize;
        pageTotal = res.data.pageTotal;
        isLast = res.data.isLast;
        $('.books').empty();
        $.each(res.data.queryList, function(index, content){
            $('.books').append('<div class="book">'
                + '<div class="title" onclick="getBookInfo(' + content.id + ');">'
                + content.title + '</div>'
                + '<div class="author">' + content.author + '</div>'
                + '<div class="book_price">¥' + content.price + '</div>'
                + '<div class="book_btn" onclick="addToCart(' + content.id + ', 1)">添加至购物车</div></div>'
            );
        });
        $('.page_total').empty();
        $('.page_total').append('共 ' + pageTotal + ' 页 当前第 ' + pageNo + ' 页' );
        $('.select').empty();
        if (pageNo !== 1)
            $('.select').append('<div onclick="Page(1);">首页</div> <div onclick="Page(pageNo-1);">上一页</div> ');
        if (pageNo !== pageTotal)
            $('.select').append('<div onclick="Page(pageNo+1);">下一页</div> <div onclick="Page(pageTotal);">末页 </div>');
        if (pageTotal !== 1)
            $('.page_total').append(' 转到 ' + '<input type="number" class="inputPageNo"> 页');
        $('.inputPageNo').change(function () {
            if (this.value <= pageTotal && this.value > 0 && this.value !== pageNo) {
                Page(this.value);
            }
            this.value = "";
        })
    }
}

function getPageList () {
    $.ajax({
        type: "GET",
        url: 'getPage?pageNo=' + pageNo + '&pageSize=' + pageSize,
        success: function(res) {
            procPage(res);
        },
        dataType: "json"
    });
}

function getSearchPageList() {
    var from = $('#from').val().replace(/[^\d]/g,"");
    $('#from').val(from);
    var to = $('#to').val().replace(/[^\d]/g,"");
    $('#to').val(to);
    if (from < 0 || to < 0)
        return;
    if (isSearch !== 1)
        pageNo = 1;
    isSearch = 1;
    $.ajax({
        type: "GET",
        url: 'getPrice?from=' + from + '&to=' + to + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        success: function(res) {
            procPage(res);
        },
        dataType: "json"
    });
}

function searchPageList() {
    var s = $('.navSearch input').val();
    if (isSearch !== 2)
        pageNo = 1;
    isSearch = 2;
    $.ajax({
        type: "GET",
        url: 'search?s=' + s + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        success: function(res) {
            procPage(res);
            $('.cover').hide();
        },
        dataType: "json"
    });
}

function getBookInfo(id) {
    $.each(bookStorge, function(index, content){
        // 找到对应信息
        if (content.id === id) {
            $('.tip').empty();
            $('.cover').empty();
            var detail = $('<div class="book_detail"><h1>' + content.title + '</h1>' +
                '<div><div class="detail">作者:</div><div class="detail_info">' + content.author + '</div></div></div>' +
                '<div><div class="detail">单价:</div><div class="detail_info">¥ ' + content.price + '</div></div></div>' +
                '<div><div class="detail">出版时间:</div><div class="detail_info">' + getLocalTime(content.publishing_date, 0) + '</div></div></div>'+
                '<div><div class="detail">库存:</div><div class="detail_info">' + content.store_number + ' 本</div></div></div>' +
                '<div><div class="detail">评论:</div><div class="detail_info">' + content.remark + '</div></div></div>'+
                '<div class="footer"><button onclick="addToCart(' + content.id + ', 1)">添加至购物车</button>' +
                ' <button onclick="$(\'.cover\').hide()">返回</button></div>' +
                '</div>');
            $('.cover').append(detail);
            $('.cover').show();
        }
    });
}

function getLocalTime(nS, code) {
    if (code === 0)
        return new Date(parseInt(nS)).toLocaleString().replace(/[\u4e00-\u9fa5][\u4e00-\u9fa5]\d{1,2}:\d{1,2}:\d{1,2}$/,' ');
    else if (code === 1)
        return new Date(parseInt(nS)).toLocaleString();
}