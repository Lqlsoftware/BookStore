var pageNo = 1;
var pageSize = 8;
var pageTotal;
var isLast;

$(document).ready(function () {
    getPageList();
});

function Page(target) {
    pageNo = target;
    getPageList();
}

function getPageList () {
    $.ajax({
        type: "GET",
        url: 'getPage?pageNo=' + pageNo + '&pageSize=' + pageSize,
        success: function(res) {

            if (res.code === 1) {
                pageNo = res.data.pageNo;
                pageSize = res.data.pageSize;
                pageTotal = res.data.pageTotal;
                isLast = res.data.isLast;
                $('.books').empty();
                $.each(res.data.queryList, function(index, content){
                    $('.books').append('<div class="book"><div class="vertical" align="left"><div class="title">' + content.title + '</div>'
                        + '<div class="author">' + content.author + '</div></div>'
                        + '<div class="horizon">¥' + content.price + ' '
                        + '<a href="#" style="margin-left: 5px">' + '加入购物车' + '</a></div></div>'
                    );
                });
                $('.total').empty();
                $('.total').append('共' + pageTotal + '页 当前第' + pageNo + '页' );
                $('.select').empty();
                if (pageNo !== 1)
                    $('.select').append('<a href="#" onclick="Page(1);">首页</a> <a href="#" onclick="Page(pageNo-1);">上一页</a> ');
                if (pageNo !== pageTotal)
                    $('.select').append('<a href="#" onclick="Page(pageNo+1);">下一页</a> <a href="#" onclick="Page(pageTotal);">末页 </a>');
                if (pageTotal === 1)
                    $('.select').append('转到' + '<input type="text" onkeydown="if(KeyCode == 13)Page(pageNo+1);">页');
            }
        },
        dataType: "json"
    });
}