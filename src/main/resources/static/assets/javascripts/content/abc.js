/**
 * 测试用
 *
 * */
(function() {
    var docDOM = $(document),
        tempData = null;

    $('form').submit(function(e) {
        e.preventDefault();

        $('.login').css({ marginTop: 200 })
        return;

        var txt = $(this).find('input').val();

        if (!txt) {
            return;
        }

        Ajax.setMockToken(txt);

        Message.success('设置成功。');

        $(this)[0].reset();
    });

    $('.btn-clear').click(function(e) {
        e.preventDefault();

        Ajax.setMockToken('');
    });

    $('.btn-test').click(function(e) {
        e.preventDefault();
        var cur = $('<img src="http://img03.taobaocdn.com/bao/uploaded/i3/720055641/TB2_Ojfeg1J.eBjy0FaXXaXeVXa_!!720055641.jpg" alt="" class="testimg" style="display: none;">')
        $('body').append(cur);
        cur.show().addClass('moving');
        setTimeout(function() {
            cur.remove();
        }, 2000)
    });

    var imgStr = 'http://img03.taobaocdn.com/bao/uploaded/i3/720055641/TB2_Ojfeg1J.eBjy0FaXXaXeVXa_!!720055641.jpg';
    var canvasDom = document.getElementById('my-canvas');
    canvasDom.width = 400;
    canvasDom.height = 400;
    canvasDom.style.backgroundColor = '#fff';
    var ctx = canvasDom.getContext('2d');

    // ctx.rect(0, 0, 400, 400);
    // ctx.arc(50, 50, 50, 0, 2 * Math.PI);
    // ctx.clip();
    // ctx.rect(0, 0, 154, 104);

    imgStr = 'http://img01.taobaocdn.com/bao/uploaded/i1/TB1FfSoNXXXXXXcXpXXXXXXXXXX_!!0-item_pic.jpg';
    // imgStr = 'http://img01.rbyair.com/20170119/c1db9a93-8b7c-4653-b47b-6ce7fcd18c57.jpg@.webp';
    // imgStr = 'http://img01.rbyair.com/20170104/342e3fc4-380b-40d2-891f-37335ba06148.jpg@.webp';

    var image = new Image();
    image.src = imgStr;
    image.onload = function() {
        var pat = ctx.createPattern(this, 'no-repeat');
        // ctx.fillStyle = pat;
        // ctx.translate(50, 50);
        // ctx.fill();
        // ctx.drawImage(this, 0, 0);
        drawImage(image);
    }

    var imgData = ctx.createImageData(100, 100);
    for (var i = 0; i < imgData.data.length; i += 4) {
        imgData.data[i + 0] = getRandom();
        imgData.data[i + 1] = getRandom();
        imgData.data[i + 2] = getRandom();
        imgData.data[i + 3] = 255;
    }
    ctx.putImageData(imgData, 10, 200);

    function getRandom() {
        return Math.round(Math.random() * 255);
    }


    // setInterval(function() {
    //     ctx.clearRect(0, 0, 400, 400);
    //     ctx.translate(50, 50);
    //     ctx.rotate(2 * Math.PI / 180);
    //     ctx.translate(-50, -50);
    //     // ctx.fill();
    //     // ctx.drawImage(image, 0, 0);
    //     drawImage(image);
    // }, 50)
    // 
    var x = 0, y = 0, angle = 0;
    setInterval(function(){
        ctx.lineTo(x, y);
        y += 400 * Math.sin(angle * Math.PI / 180);
        x +=  (400 - 400 * Math.cos(angle * Math.PI / 180));
        angle += 20;
        // ctx.clearRect(0, 0, 400, 400);
        // ctx.translate(x, y);
        // drawImage(image)
        ctx.stroke();
        console.log(angle)
    },500)

    function drawImage(img) {
        ctx.drawImage(img, 100, 100, 300, 300, 0, 0, 100, 100 );
    }

    function rotate() {
        var x = canvasDom.width / 2; //画布宽度的一半
        var y = canvasDom.height / 2; //画布高度的一半
        ctx.clearRect(0, 0, canvasDom.width, canvasDom.height); //先清掉画布上的内容
        ctx.translate(x, y); //将绘图原点移到画布中点
        ctx.rotate((Math.PI / 180) * 5); //旋转角度
        ctx.translate(-x, -y); //将画布原点移动
        ctx.drawImage(image, 0, 0); //绘制图片     
    }

    // setInterval(rotate, 100)

    // ctx.drawImage(image, 100, 100, 50, 50);
    ctx.strokeStyle = '#f00';
    ctx.strokeText('hello', 100, 100)
})()
