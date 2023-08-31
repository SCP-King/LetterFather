$(document).ready(function() {
    'use strict';

    let x_data=[]
    let y_data=[]
    let color=[]


    $.ajax(
        {
            url:"/data3",
            method:"get",
            success:function (resp){
                let arr=resp

                for(let i=0;i<arr.length;i++){
                    x_data.push(arr[i].type)
                    y_data.push(arr[i].letter_num)
                    if(arr[i].type==="咨询"){
                        color.push("pink")
                    }else {
                        color.push("blue")
                    }

                }
                var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
                var lineChart = new Chart(lineChartCanvas, {
                    type: 'doughnut',
                    data: data_1,
                    options: options
                });

            },
            error:function (){
                alert("获取数据失败，系统出错")
            }

        }
    )


    let data_1={
        labels:x_data,
        datasets:[
            {
                data:y_data,
                backgroundColor:color,
                borderColor:color
            }
        ]
    }
    let options = {
        title: {
            display: true,
            text: '信件类型分布',
            fontSize: 16
        },
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        },
        legend: {
            display: true,
            position:"top"
        }

    };
});