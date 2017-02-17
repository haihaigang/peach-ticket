/**
 * 搜索
 *
 * */
(function () {
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
        }, function (response) {
            Message.success('购票成功，请去订单中查看');
        });
    });

    $('#startStationId,#endStationId').autocomplete({
        source: function (query, process) {
            var matchCount = this.options.items;//返回结果集最大数量
            Ajax.custom({
                url: Ajax.javaPath + "/station/findByCode",
                data: {
                    code: query
                }
            }, function (response) {
                return process(response.body);
            });
        },
        formatItem: function (item) {
            return item.name + "(" + item.code + "，" + item.id + ")";
        },
        setValue: function (item) {
            return {'data-value': item.name, 'real-value': item.id};
        }
    });

    /**
     * 搜索商品
     */
    function getList(page) {
        var obj = {
            startStationId: $('#startStationId').attr('real-value'),
            endStationId: $('#endStationId').attr('real-value'),
        };

        Ajax.custom({
            url: Ajax.javaPath + '/line/findByStations',
            data: obj,
            showLoader: true
        }, function (response) {
            tempData = response.body;
            Ajax.render("#peach-page", "peach-page-tpl", tempData);
            Ajax.render("#peach-list", "peach-list-tpl", tempData);


        }, function (data) {
        })
    }


})()
