// excelReader.ts
import * as XLSX from 'xlsx';

export function readExcel(file: File, columnToProperty: { [key: string]: string }): Promise<{ [key: string]: any }[]> {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = (e) => {
            const data = new Uint8Array(e.target!.result as ArrayBuffer);
            const workbook = XLSX.read(data, { type: 'array' });

            const worksheetName = workbook.SheetNames[0];
            const worksheet = workbook.Sheets[worksheetName];

            // 将工作表转换为JSON对象数组
            const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

            // 将数据转换为对象数组
            const objects = jsonData.slice(1).map((row:any) => {
                const obj: { [key: string]: any } = {}; // Declare obj as an indexable type
                for (let i = 0; i < row.length; i++) {
                    // @ts-ignore
                    const column = jsonData[0][i];
                    const property = columnToProperty[column];
                    // // 如果当前列是"资源类型标识"，则将值转换为数字
                    // if (property === 'srcType') {
                    //     obj[property] = Number(row[i]);
                    // } else {
                    //     obj[property] = row[i];
                    // }
                    obj[property] = row[i];
                }
                return obj;
            });
            resolve(objects);
        };
        reader.onerror = (e) => {
            reject(e);
        };
        reader.readAsArrayBuffer(file);
    });
}
