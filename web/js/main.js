var pageNo = 1;
var pageSize = 10;
var pageTotal;
var isLast;

$(document).ready(function () {

    getPageList();


});

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
                $('.book').empty();
                $.each(res.data.queryList, function(index, content){
                    $('.book').append('<p align="center">' + content.title + ' '
                        + content.author + ' '
                        + content.price + ' '
                        + '加入购物车' + '</p>'
                    );
                });
            }
        },
        dataType: "json"
    });
}