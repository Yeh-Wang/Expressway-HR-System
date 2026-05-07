import {ApplyFeedback, dailyPunch} from "../attendanceManage/type.ts";
import {StaffSchedule} from "../scheduleManage/type.ts";
import {ExamInfo, ExamPaperInfo, StudyRecord, StudySrc, TrainInfo} from "../TrainManagement/type.ts";

//该员工的每日打卡记录
export const dailyPunchList = ref([] as dailyPunch[]);

//该员工的每日请假记录信息
export const leaveAppList = ref( [] as ApplyFeedback[]);

//该员工的所有排班信息
export const staffScheduleList = ref([] as StaffSchedule[]);


//所有培训计划信息
export const staffTrainInfoList = ref([] as TrainInfo[]);

//该职员的学习记录信息
export const staffStudyRecordList = ref([] as StudyRecord[]);

//所有考试信息
export const staffExamInfoList = ref([] as ExamInfo[]);

export const currentQuestion = ref([] as StudySrc[])
export const currentPaperInfo = ref({} as ExamPaperInfo)
export const currentExamInfo = ref({} as ExamInfo)

export const mockQuestion = [
    {
        "srcId": 1,
        "srcType": "判断题",
        "srcTitle": "收费站员工在工作期间必须穿着统一的工作服。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "收费站员工为了保持专业形象和便于管理，通常需要穿着统一的工作服。",
        "srcTrain": 1
    },
    {
        "srcId": 2,
        "srcType": "单选题",
        "srcTitle": "以下哪项不是收费站的优惠政策？",
        "srcAnswer": "C",
        "srcScore": 10,
        "srcSelect": "A. 节假日免费通行 B. 军车免费通行 C. 所有车辆免费通行 D. 绿色通道政策",
        "srcAnalysis": "所有车辆免费通行不是收费站的标准优惠政策。",
        "srcTrain": 1
    },
    {
        "srcId": 3,
        "srcType": "单选题",
        "srcTitle": "收费站的收费标准是如何公示的？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 口头告知 B. 电子显示屏 C. 纸质公告 D. 短信通知",
        "srcAnalysis": "通常，收费站的收费标准会通过电子显示屏进行公示，以便于司机在进入收费站前就能看到。",
        "srcTrain": 1
    },
    {
        "srcId": 4,
        "srcType": "单选题",
        "srcTitle": "收费站的收费员需要具备哪些基本技能？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 良好的沟通能力 B. 快速计算能力 C. 车辆识别能力 D. 所有以上",
        "srcAnalysis": "作为收费员，需要具备良好的沟通能力、快速计算能力和车辆识别能力，以确保收费过程的顺利进行。",
        "srcTrain": 1
    },
    {
        "srcId": 5,
        "srcType": "单选题",
        "srcTitle": "以下哪项情况下，车辆不需要停车接受检查？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 节假日免费通行 B. 军车免费通行 C. 未按规定缴纳通行费 D. 车辆损坏",
        "srcAnalysis": "车辆损坏并不是停车接受检查的直接原因，除非损坏影响到行车安全或造成其他问题。",
        "srcTrain": 1
    },
    {
        "srcId": 6,
        "srcType": "单选题",
        "srcTitle": "以下哪些属于收费站员工的职业道德规范？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 诚实守信 B. 公平公正 C. 尊重客户 D. 遵守工作纪律",
        "srcAnalysis": "收费站员工的职业道德规范包括诚实守信、公平公正、尊重客户和遵守工作纪律。",
        "srcTrain": 1
    },
    {
        "srcId": 7,
        "srcType": "单选题",
        "srcTitle": "收费站的收费标准是如何确定的？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 随意定价 B. 政府规定 C. 市场调节 D. 客户投票",
        "srcAnalysis": "收费站的收费标准通常由政府相关部门根据法律法规和实际情况进行规定。",
        "srcTrain": 1
    },
    {
        "srcId": 8,
        "srcType": "单选题",
        "srcTitle": "以下哪项不是收费站的收费方式？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 人工收费 B. 不支付 C. 手机支付 D. 现金支付",
        "srcAnalysis": "所有列出的选项都是收费站可能采用的收费方式。",
        "srcTrain": 1
    },
    {
        "srcId": 9,
        "srcType": "判断题",
        "srcTitle": "收费站员工在处理突发事件时，应保持冷静并迅速上报。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "在处理突发事件时，保持冷静并迅速上报是确保事件得到及时处理的关键步骤。",
        "srcTrain": 1
    },
    {
        "srcId": 10,
        "srcType": "判断题",
        "srcTitle": "收费站员工在工作期间可以与客户发生争执。",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "收费站员工应始终保持专业和礼貌，避免与客户发生争执。",
        "srcTrain": 1
    },
    {
        "srcId": 11,
        "srcType": "单选题",
        "srcTitle": "收费站的收费方式不包括以下哪项？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 人工收费 B. 不支付 C. 手机支付 D. 现金支付",
        "srcAnalysis": "所有列出的选项都是收费站可能采用的收费方式。",
        "srcTrain": 1
    },
    {
        "srcId": 12,
        "srcType": "判断题",
        "srcTitle": "收费站员工在处理客户投诉时，应保持耐心并及时解决问题。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "无论何时，保持专业和耐心是处理客户投诉的关键。",
        "srcTrain": 1
    },
    {
        "srcId": 13,
        "srcType": "单选题",
        "srcTitle": "以下哪项不是收费站的标准优惠政策？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 节假日免费通行 B. 军车免费通行 C. 救护车免费通行 D. 所有车辆免费通行",
        "srcAnalysis": "所有车辆免费通行通常不是收费站的标准优惠政策。",
        "srcTrain": 1
    },
    {
        "srcId": 14,
        "srcType": "判断题",
        "srcTitle": "收费站员工可以在任何情况下离开工作岗位。",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "员工应遵守工作规定，不得在未授权的情况下离开工作岗位。",
        "srcTrain": 1
    },
    {
        "srcId": 15,
        "srcType": "单选题",
        "srcTitle": "收费站的收费记录需要保存至少多少年？",
        "srcAnswer": "C",
        "srcScore": 10,
        "srcSelect": "A. 1年 B. 2年 C. 5年 D. 10年",
        "srcAnalysis": "根据相关法规，收费记录的保存年限有具体要求。",
        "srcTrain": 1
    },
    {
        "srcId": 16,
        "srcType": "判断题",
        "srcTitle": "收费站的监控系统主要用于安全监控和交通流量分析。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "监控系统确实用于这些目的，以确保收费站的安全和效率。",
        "srcTrain": 1
    },
    {
        "srcId": 17,
        "srcType": "单选题",
        "srcTitle": "收费站的收费系统通常由哪些部门负责监管？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 交通管理部门 B. 公安部门 C. 税务部门 D. 所有以上",
        "srcAnalysis": "收费系统的监管可能涉及多个部门，具体取决于当地的法律法规。",
        "srcTrain": 1
    },
    {
        "srcId": 18,
        "srcType": "单选题",
        "srcTitle": "以下哪项不属于收费站的常规优惠政策？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 节假日免费通行 B. 不让通行 C. 救护车免费通行 D. 执行公务的车辆免费通行",
        "srcAnalysis": "所有列出的选项通常都属于收费站的优惠政策，除非有特别说明。",
        "srcTrain": 1
    },
    {
        "srcId": 19,
        "srcType": "判断题",
        "srcTitle": "收费站员工在工作期间可以随意更改收费标准。",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "收费标准是由相关部门制定的，员工无权随意更改。",
        "srcTrain": 1
    },
    {
        "srcId": 20,
        "srcType": "单选题",
        "srcTitle": "收费站的优惠政策不包括以下哪项？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 节假日免费通行 B. 不让通行 C. 救护车免费通行 D. 执行公务的车辆免费通行",
        "srcAnalysis": "所有列出的选项通常都属于收费站的优惠政策，除非有特别说明。",
        "srcTrain": 1
    },
    {
        "srcId": 21,
        "srcType": "判断题",
        "srcTitle": "收费站在遇到系统故障时，应立即启动应急预案。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "系统故障可能影响收费流程，启动应急预案是确保交通流畅和安全的重要措施。",
        "srcTrain": 1
    },
    {
        "srcId": 22,
        "srcType": "单选题",
        "srcTitle": "收费站通常采用哪些收费方式？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 人工收费 B. 电子收费 C. 移动支付 D. 以上都是",
        "srcAnalysis": "随着技术的发展，收费站采用多种收费方式以适应不同需求。",
        "srcTrain": 1
    },
    {
        "srcId": 23,
        "srcType": "判断题",
        "srcTitle": "收费站员工在工作期间不得使用手机。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "为了确保工作的专注和效率，员工在工作期间应避免使用手机。",
        "srcTrain": 1
    },
    {
        "srcId": 24,
        "srcType": "单选题",
        "srcTitle": "鲜活农产品运输车辆预约成功后，自预约起多少时间内未驶入高速公路的，当次预约自动失效？",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 1小时 B. 2小时 C. 3小时 D. 4小时",
        "srcAnalysis": "预约系统设定时间限制以确保高速公路资源的有效利用。",
        "srcTrain": 1
    },
    {
        "srcId": 25,
        "srcType": "判断题",
        "srcTitle": "收费站的收费标准是全国统一的。",
        "srcAnswer": "B",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "收费标准可能因地区、道路类型和当地政策而有所不同。",
        "srcTrain": 1
    },
    {
        "srcId": 26,
        "srcType": "单选题",
        "srcTitle": "收费站的免费通行政策通常适用于哪些时段？",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 节假日 B. 夜间 C. 任何时间 D. 仅工作日",
        "srcAnalysis": "免费通行政策通常在特定时段实施，如节假日或夜间。",
        "srcTrain": 1
    },
    {
        "srcId": 27,
        "srcType": "判断题",
        "srcTitle": "收费站员工在遇到紧急情况时，应立即联系上级并采取相应措施。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "紧急情况下迅速反应是保障人员和车辆安全的重要步骤。",
        "srcTrain": 1
    },
    {
        "srcId": 28,
        "srcType": "单选题",
        "srcTitle": "收费站出口前多少公里范围内，应设置明显的指示标志？",
        "srcAnswer": "D",
        "srcScore": 10,
        "srcSelect": "A. 5公里 B. 10公里 C. 15公里 D. 20公里",
        "srcAnalysis": "设置指示标志有助于驾驶者提前做好准备，避免错过出口。",
        "srcTrain": 1
    },
    {
        "srcId": 29,
        "srcType": "判断题",
        "srcTitle": "收费站员工应熟悉所有相关的交通法规和政策。",
        "srcAnswer": "A",
        "srcScore": 10,
        "srcSelect": "A. 正确 B. 错误",
        "srcAnalysis": "了解相关法规和政策对于提供准确信息和优质服务至关重要。",
        "srcTrain": 1
    },
    {
        "srcId": 30,
        "srcType": "单选题",
        "srcTitle": "收费站员工的业务培训应该多久进行一次？",
        "srcAnswer": "C",
        "srcScore": 10,
        "srcSelect": "A. 1个月 B. 3个月 C. 6个月 D. 1年",
        "srcAnalysis": "定期培训有助于保持员工的业务能力和服务水平。",
        "srcTrain": 1
    }
]
