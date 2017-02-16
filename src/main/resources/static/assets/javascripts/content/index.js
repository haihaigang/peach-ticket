/**
 * 搜索
 *
 * */
(function() {
    var docDOM = $(document),
        tempData = null,
        focusInte,
        previewDom = $('.peach-preview');

    //提交form表单
    $('.peach-search').submit(function (e) {
        e.preventDefault();

        getList(1);
    });

    docDOM.on('click', '.btn-buy', function (e) {
        e.preventDefault();

        var idx = $(this).data('idx'),
            d = tempData[idx];

        Ajax.custom({
            url: Ajax.javaPath + '/order/submit',
            data: {
                startStationId: d.startStationId,
                endStationId: d.endStationId,
                fangan: d.fangan
            },
            type: 'POST'
        },function(response){
            Message.success('购票成功，请去订单中查看');
        });
    })

    /**
     * 搜索商品
     */
    function getList(page) {
        var formData = $('.peach-search').serializeArray();
        var obj = {};

        for (var i in formData) {
            obj[formData[i].name] = formData[i].value;
        }
        obj.page = page;
        obj.size = Ajax.pageSize;

        Ajax.custom({
            url: Ajax.javaPath + '/line/findByStations',
            data: obj,
            showLoader: true
        }, function(response) {
            tempData = response.body;
            Ajax.render("#peach-page", "peach-page-tpl", tempData);
            Ajax.render("#peach-list", "peach-list-tpl", tempData);


        }, function(data) {
        })
    }


})()
