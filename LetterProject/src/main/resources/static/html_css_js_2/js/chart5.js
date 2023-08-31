$(document).ready(function() {
    'use strict';

    let x_data=[]
    let y_data=[]
    let color=[]
    const colors = [
        'rgb(255, 99, 132)',
        'rgb(54, 162, 235)',
        'rgb(255, 205, 86)',
        'rgb(75, 192, 192)',
        'rgb(153, 102, 255)',
        'rgb(255, 159, 64)',
        'rgb(255, 0, 0)',
        'rgb(0, 255, 0)',
        'rgb(0, 0, 255)',
        'rgb(255, 255, 0)',
        'rgb(0, 255, 255)',
        'rgb(255, 0, 255)',
        'rgb(128, 0, 0)',
        'rgb(0, 128, 0)',
        'rgb(0, 0, 128)',
        'rgb(128, 128, 0)',
        'rgb(0, 128, 128)',
        'rgb(128, 0, 128)',
        'rgb(128, 128, 128)',
        'rgb(192, 192, 192)',
        'rgb(255, 99, 132)',
        'rgb(54, 162, 235)',
        'rgb(255, 205, 86)',
        'rgb(75, 192, 192)',
        'rgb(153, 102, 255)',
        'rgb(255, 159, 64)',
        'rgb(255, 0, 0)',
        'rgb(0, 255, 0)',
        'rgb(0, 0, 255)',
        'rgb(255, 255, 0)',
        'rgb(0, 255, 255)',
        'rgb(255, 0, 255)',
        'rgb(128, 0, 0)',
        'rgb(0, 128, 0)',
        'rgb(0, 0, 128)',
        'rgb(128, 128, 0)',
        'rgb(0, 128, 128)',
        'rgb(128, 0, 128)',
        'rgb(128, 128, 128)',
        'rgb(192, 192, 192)',
        'rgb(255, 99, 132)',
        'rgb(54, 162, 235)',
        'rgb(255, 205, 86)',
        'rgb(75, 192, 192)',
        'rgb(153, 102, 255)',
        'rgb(255, 159, 64)',
        'rgb(255, 0, 0)',
        'rgb(0, 255, 0)',
        'rgb(0, 0, 255)',
        'rgb(255, 255, 0)',
        'rgb(0, 255, 255)',
        'rgb(255, 0, 255)',
        'rgb(128, 0, 0)',
        'rgb(0, 128, 0)',
        'rgb(0, 0, 128)',
        'rgb(128, 128, 0)',
        'rgb(0, 128, 128)',
        'rgb(128, 0, 128)',
        'rgb(128, 128, 128)',
        'rgb(192, 192, 192)'
    ];



    $.ajax(
        {
            url:"/data5",
            method:"get",
            success:function (resp){
                let arr=resp

                for(let i=0;i<arr.length;i++){
                    x_data.push(arr[i].organization)
                    y_data.push(arr[i].letter_num)
                    color.push(colors[i])

                }
                var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
                var lineChart = new Chart(lineChartCanvas, {
                    type: 'pie',
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
            text: '信件类部门布',
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