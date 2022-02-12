var $blin = $('.light p'), //所有彩灯
    $prize = $('.play li').not('#btn'), //含谢谢参与的所有奖品
    $change = $('#change'), //显示剩余机会
    $btn = $('#btn'), //开始抽奖按钮
    length = $prize.length, //奖品总数
    data = { count: 5 }, //次数
    bool = true, //判断是否可点击,true为可点击
    mark = 0, //标记当前位置，区间为0-7
    timer //定时器

//重新获取
function reset() {
    $blin = $('.light p'), //所有彩灯
        $prize = $('.play li').not('#btn'), //含谢谢参与的所有奖品
        $change = $('#change'), //显示剩余机会
        $btn = $('#btn'), //开始抽奖按钮
        length = $prize.length, //奖品总数
        data = { count: 5 }, //次数
        bool = true, //判断是否可点击,true为可点击
        mark = 0, //标记当前位置，区间为0-7
        timer //定时器
    //点击抽奖
    $btn.click(function () {
        if (bool) {
            //若未按下
            bool = false
            data.count--
            $change.html(data.count)
            getPrize()
        }
    })

    //中奖信息提示
    $('#close,.win,.btn').click(function () {
        clearInterval(timer) //关闭弹出时清除定时器
        cjInit()
    })
}

// $(function () {
//     //点击抽奖
//     $btn.click(function () {
//         if (bool) {
//             //若未按下
//             bool = false
//             data.count--
//             $change.html(data.count)
//             getPrize()
//         }
//     })

//     //中奖信息提示
//     $('#close,.win,.btn').click(function () {
//         clearInterval(timer) //关闭弹出时清除定时器
//         cjInit()
//     })
// })
window.onload = function () {
    //初始化判断用户是否为空
    var empObj = JSON.parse(localStorage.getItem("emp"))
    if (empObj == null || empObj.empNo == null || empObj.name == null) {
        vant.Toast.fail("暂未登录需要重新登录")
        setTimeout(() => {
            window.location.href = "./index.html"
        }, 3000);
    }
    getList(empObj)
    // cjInit()
}
const { jobNumber } = _GET()

//默认动画效果
function cjInit() {
    timer = setInterval(function () {
        //不能调用animate函数，不然标记会有问题
        $blin.toggleClass('blin') //彩灯动画
        //九宫格动画
        length++
        length %= 8
        $prize.eq(length - 1).removeClass('select')
        $prize.eq(length).addClass('select')

        //位置标记的改变
        mark++
        mark %= 8
    }, 1000)
}

//点击旋转
function clickFn(selectData) {
    console.log("选中到的序号是：",selectData)
    // selectData = 7
    $('.play li').removeClass('select')
    $('.play li').first().addClass('select')
    clearInterval(timer) //点击抽奖时清除定时器
    var random = [8,1, 2, 3, 4, 5, 6, 7] //抽奖概率
    //data为随机出来的结果，根据概率后的结果
    // var selectData = Math.floor(Math.random() * random.length)
    random = random[selectData] //1-8的随机数
    length = 0
    mark = 0
    mark += random
    mark %= 8
    //默认先转4圈
    random += 32 //圈数 * 奖品总数
    //调用旋转动画
    for (var i = 1; i <= random; i++) {
        setTimeout(animate(), 2 * i * i) //第二个值越大，慢速旋转时间越长
    }
    //停止旋转动画
    setTimeout(function () {
        setTimeout(function () {
            bool = true
            win()
        }, 1000)
    }, 2 * random * random)
}

//动画效果
function animate() {
    return function () {
        $blin.toggleClass('blin') //彩灯动画
        //九宫格动画
        length++
        length %= 8
        $prize.eq(length - 1).removeClass('select')
        $prize.eq(length).addClass('select')
    }
}

async function getList(empObj = {}) {
    //先查询用户是否已经中奖，如果中奖：展示中奖信息div，如果没中：展示抽奖div
    const params = {}
    const datas = {}
    datas.empNo = empObj.empNo
    datas.name = empObj.name
    const result = await postJSON(`/openapi/cj/getEmpPrize`, params, datas)
    if (result && result.data && result.data.name != null) {
        $('.main').remove()
        $('.win-user').text(`员工编号：${empObj.empNo}，姓名：${empObj.name}`)
        if(result.data.win == 'N'){
            $('.red-body').css("background-image","url('../image/bottom_no.png')");
        }else{
            $('.winning-body').text(result.data.info)
            $('.winning-body').attr('title', result.data.info)
        }
        win()
    } else {
        //获取奖品列表，然后渲染数据
        const params = {}
        const datas = {}
        const result = await postJSON(`/openapi/cj/getPrizeList`, params, datas)
        const prizeList = result.data
        localStorage.setItem("prizeList", JSON.stringify(prizeList));
        var mainstr = ""
        for (const i in prizeList) {
            if (i == 0) {
                mainstr = mainstr + `<li class="prize select">
                <img src="${prizeList[i].backImg}" class="prize-back-img" />
                <div>
                    <p>${prizeList[i].name}</p>
                </div>
            </li>`
            } else {
                if (i == 4) {
                    mainstr = mainstr + `<li id="btn"></li>`
                }
                mainstr = mainstr + `<li class="prize">
                    <img src="${prizeList[i].backImg}" class="prize-back-img"/>
                    <div>
                        <p>${prizeList[i].name}</p>
                    </div>
                </li>`
            }
        }
        $('#playcontainer').empty().html(mainstr)
        reset()
        $('.main').show()
        cjInit()
    }
}

async function getPrize() { 
    try {
        const params = {}
        const datas = {}
        var empObj = JSON.parse(localStorage.getItem("emp"))
        datas.empNo = empObj.empNo
        const { data } = await postJSON(`/openapi/cj/cj`, params, datas)
        var prizeList = JSON.parse(localStorage.getItem("prizeList"))
        var selectIdList = []
        var selectId
        for (const i in prizeList) {
            if (data.id === prizeList[i].id) {
                selectIdList.push(i)
            }
        }
        console.log("抽奖结果是:",data)
        var selectId = selectIdList[Math.floor(Math.random() * selectIdList.length)]
        if(data.win == 'N'){
            //TODO 修改图片
            $('.red-body').css("background-image","url('../image/bottom_no.png')");
        }else{
            $('.winning-body').text(data.info)
            $('.winning-body').attr('title', data.info)
        }
        $('.win-user').text(`员工编号：${empObj.empNo}，姓名：${empObj.name}`)
        clickFn(selectId)
    } catch (error) {
        console.log(error)
    }
    bool = true
}
