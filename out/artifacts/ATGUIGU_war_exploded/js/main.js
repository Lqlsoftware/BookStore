var pageNo = 1;
var pageSize = 7;
var pageTotal;
var isLast;
var isSearch = false;
var bookStorge;
var cartStorge;

$(document).ready(function () {
    getCart();
    getPageList();
    $('.searchbtn').click(function () {
        getSearchPageList();
    });
    $('.cover').hide();
});

function procCart(res, code) {
    if (res.code === 1) {
        cartStorge = res.data;
        $('.tip').empty();
        $('.tip').append(res.errMsg);
        $('.mycart').empty();
        $('.mycart').append('您的购物车中有' + res.data.num + '件商品 ' +
        '<button class="checkCart" onclick="getCartInfo();">查看购物车</button>');
        if (code === 1)
            getCartInfo();
        else if (code === 2) {
            $('.total').empty();
            $('.total').append('您的购物车中有' + res.data.num + '件商品');
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
    var total = $('<div class="total">您的购物车中有' + cartStorge.num + '件商品</div>')
    var header = $('<div class="cart_header"><div class="headName">书名</div>' +
        '<div class="headNum">数量</div>' +
        '<div class="headPrice">价格</div></div>');
    var body = $('<div class="cart_body"></div>')
    var footer = $( '<div class="footer">' +
        '<div style="margin-top: 10px;margin-bottom: 10px">总金额: ¥' + cartStorge.total + '</div>' +
        '<button onclick="clearCart();">清空购物车</button>' +
        ' <button onclick="getPay();">结账</button>' +
        ' <button onclick="$(\'.cover\').hide();">返回</button></div>')

    $.each(cartStorge.queryList, function(index, content) {
        var cartItem = $('<div class="cartItem"></div>');
        cartItem.append(
            '<div class="itemName">' + content.book.title + '</div>' +
            '<div class="itemNum"><input onchange="setCart(' + content.book.id + ',this.value);" type="number" value="' + content.quantity + '"></div>' +
            '<div class="itemPrice">¥ ' + content.totalMoney +
            ' <button class="del" onclick="setCart(' + content.book.id + ',0);">删除</button></div>'
        );
        body.append(cartItem);
    });
    detail.append(total);
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
                var trade_detail = $('<div class="trade_time">交易时间: ' + res.data.trade_time + '</div>' +
                    '<div class="trade_money">总计 ¥ ' + res.data.total + '</div>');
                var trade_head = $('<div><div class="headName">书名</div>' +
                    '<div class="headPrice">单价</div>' +
                    '<div class="headNum">数量</div></div>');
                trade_detail.append(trade_head);
                $.each(res.data.queryList, function (index, context) {
                    trade_detail.append('<div class="itemName">' + context.book.title + '</div>' +
                        '<div class="itemPrice">' + context.book.price + '</div>' +
                        '<div class="itemNum">' + context.quantity + '</div>')
                })
                var trade_footer = $( '<div class="footer"><button onclick="$(\'.cover\').hide();">返回</button></div>');

                trade.append(trade_title);
                trade.append(trade_user);
                trade.append(trade_detail);
                trade.append(trade_footer);
                $('.cover').append(trade);
                $('.cover').show();

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
    if (isSearch === true)
        getSearchPageList();
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
            $('.books').append('<div class="book"><div class="vertical" align="left"><div class="title"><a href="#" onclick="getBookInfo(' + content.id + ');">' + content.title + '</a></div>'
                + '<div class="author">' + content.author + '</div></div>'
                + '<div class="horizon">¥' + content.price + ' '
                + '<button onclick="addToCart(' + content.id + ', 1)">添加至购物车</button></div></div>'
            );
        });
        $('.page_total').empty();
        $('.page_total').append('共 ' + pageTotal + ' 页 当前第 ' + pageNo + ' 页' );
        $('.select').empty();
        if (pageNo !== 1)
            $('.select').append('<a href="#" onclick="Page(1);">首页</a> <a href="#" onclick="Page(pageNo-1);">上一页</a> ');
        if (pageNo !== pageTotal)
            $('.select').append('<a href="#" onclick="Page(pageNo+1);">下一页</a> <a href="#" onclick="Page(pageTotal);">末页 </a>');
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
    var from = $('#from').val();
    var to = $('#to').val();
    if (from === "" || from < 0
        || to === "")
        return;
    if (isSearch === false)
        pageNo = 1;
    isSearch = true;
    $.ajax({
        type: "GET",
        url: 'getPrice?from=' + from + '&to=' + to + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        success: function(res) {
            procPage(res);
        },
        dataType: "json"
    });
}

function getBookInfo(id) {
    $.each(bookStorge, function(index, content){
        // 找到对应信息
        if (content.id === id) {
            var total = $('<div class="total">您的购物车中有' + cartStorge.num + '件商品</div>')
            $('.tip').empty();
            $('.cover').empty();
            var detail = $('<div class="book_detail"><div class="detail">书名:' + content.title + '</div>' +
                '<div class="detail">作者:' + content.author + '</div>' +
                '<div class="detail">单价: ¥' + content.price + '</div>' +
                '<div class="detail">出版时间: ' + getLocalTime(content.publishing_date) + '</div>'+
                '<div class="detail">库存: ' + content.store_number + '</div>' +
                '<div class="detail">评论: ' + content.remark + '</div>'+
                '<div class="footer"><button onclick="addToCart(' + content.id + ', 1)">添加至购物车</button>' +
                ' <button onclick="$(\'.cover\').hide()">返回</button></div>' +
                '</div>');
            $('.cover').append(total);
            $('.cover').append(detail);
            $('.cover').show();
        }
    });
}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/[\u4e00-\u9fa5][\u4e00-\u9fa5]\d{1,2}:\d{1,2}:\d{1,2}$/,' ');
}