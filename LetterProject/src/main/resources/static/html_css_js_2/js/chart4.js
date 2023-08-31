$(document).ready(function() {
    'use strict';

    let x_data=[]
    let y_data=[]


    $.ajax(
        {
            url:"/data4",
            method:"get",
            success:function (resp){
                let arr=resp

                for(let i=0;i<arr.length;i++){
                    x_data.push(arr[i].toad)
                    y_data.push(arr[i].toad_num)

                }
                var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
                var lineChart = new Chart(lineChartCanvas, {
                    type: 'bar',
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
                label:"时间差",
                data:y_data,
                backgroundColor:"blue",
                borderColor:"blue",
                borderWidth: 1,
                fill:false
            }
        ]
    }
    let options = {
        title: {
            display: true,
            text: '时间差分布',
            fontSize: 16
        },
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        legend: {
            display: true,
            position:"top"
        }

    };
});