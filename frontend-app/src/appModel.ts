export interface ProfessionModel {
    id?: number,
    name: string,
    note: string
}

export interface DepartmentModel {
    id?: number,
    name: string,
    note: string,
    parentDepartmentId: number | undefined | null,
    children?: DepartmentModel[]
}

export interface EmployeeModel {
    id?: number,
    firstName: string,
    lastName: string,
    middleName: string,
    profession: ProfessionModel,
    department: DepartmentModel,
    note: string
}