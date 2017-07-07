var pageNo = 1;
var pageSize = 7;
var pageTotal;
var isLast;
var isSearch = false;
var storge;

$(document).ready(function () {
    getCart();
    getPageList();
    $('.searchbtn').click(function () {
        getSearchPageList();
    });
    $('.cover').hide();
});

function getCart() {
    
}



function Page(target) {
    pageNo = target;
    if (isSearch === true)
        getSearchPageList();
    else
        getPageList();
}

function getPageList () {
    $.ajax({
        type: "GET",
        url: 'getPage?pageNo=' + pageNo + '&pageSize=' + pageSize,
        success: function(res) {

            if (res.code === 1) {
                storge = res.data.queryList;
                pageNo = res.data.pageNo;
                pageSize = res.data.pageSize;
                pageTotal = res.data.pageTotal;
                isLast = res.data.isLast;
                $('.books').empty();
                $.each(res.data.queryList, function(index, content){
                    $('.books').append('<div class="book"><div class="vertical" align="left"><div class="title"><a href="#" onclick="getBookInfo(' + content.id + ');">' + content.title + '</a></div>'
                        + '<div class="author">' + content.author + '</div></div>'
                        + '<div class="horizon">¥' + content.price + ' '
                        + '<button>添加至购物车</button></div></div>'
                    );
                });
                $('.total').empty();
                $('.total').append('共 ' + pageTotal + ' 页 当前第 ' + pageNo + ' 页' );
                $('.select').empty();
                if (pageNo !== 1)
                    $('.select').append('<a href="#" onclick="Page(1);">首页</a> <a href="#" onclick="Page(pageNo-1);">上一页</a> ');
                if (pageNo !== pageTotal)
                    $('.select').append('<a href="#" onclick="Page(pageNo+1);">下一页</a> <a href="#" onclick="Page(pageTotal);">末页 </a>');
                if (pageTotal !== 1)
                    $('.total').append(' 转到 ' + '<input type="number" class="inputPageNo"> 页');
                $('.inputPageNo').change(function () {
                    if (this.value <= pageTotal && this.value > 0 && this.value !== pageNo) {
                        Page(this.value);
                    }
                    this.value = "";
                })
            }
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
            if (res.code === 1) {
                storge = res.data.queryList;
                pageNo = res.data.pageNo;
                pageSize = res.data.pageSize;
                pageTotal = res.data.pageTotal;
                isLast = res.data.isLast;
                $('.books').empty();
                $.each(res.data.queryList, function(index, content){
                    $('.books').append('<div class="book"><div class="vertical" align="left"><div class="title"><a href="#" onclick="getBookInfo(' + content.id + ');">' + content.title + '</a></div>'
                        + '<div class="author">' + content.author + '</div></div>'
                        + '<div class="horizon">¥' + content.price + ' '
                        + '<button>添加至购物车</button></div></div>'
                    );
                });
                $('.total').empty();
                $('.total').append('共 ' + pageTotal + ' 页 当前第 ' + pageNo + ' 页' );
                $('.select').empty();
                if (pageNo !== 1)
                    $('.select').append('<a href="#" onclick="Page(1);">首页</a> <a href="#" onclick="Page(pageNo-1);">上一页</a> ');
                if (pageNo !== pageTotal)
                    $('.select').append('<a href="#" onclick="Page(pageNo+1);">下一页</a> <a href="#" onclick="Page(pageTotal);">末页 </a>');
                if (pageTotal !== 1)
                    $('.total').append(' 转到 ' + '<input type="number" class="inputPageNo"> 页');
                $('.inputPageNo').change(function () {
                    if (this.value <= pageTotal && this.value > 0 && this.value !== pageNo) {
                        Page(this.value);
                    }
                    this.value = "";
                });
            }
        },
        dataType: "json"
    });
}

function getBookInfo(id) {
    $.each(storge, function(index, content){
        // 找到对应信息
        if (content.id === id) {
            $('.cover').empty();
            var detail = $('<div class="book_detail"><div class="detail">书名:' + content.title + '</div>' +
                '<div class="detail">作者:' + content.author + '</div>' +
                '<div class="detail">单价: ¥' + content.price + '</div>' +
                '<div class="detail">出版时间: ' + getLocalTime(content.publishing_date) + '</div>'+
                '<div class="detail">库存: ' + content.store_number + '</div>' +
                '<div class="detail">评论: ' + content.remark + '</div>'+
                '<div class="detail"><button onclick="$(\'.cover\').hide()">添加至购物车</button>' +
                ' <button onclick="$(\'.cover\').hide()">返回</button></div>' +
                '</div>');
            $('.cover').append(detail);
            $('.cover').show();
        }
    });
}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/[\u4e00-\u9fa5][\u4e00-\u9fa5]\d{1,2}:\d{1,2}:\d{1,2}$/,' ');
}