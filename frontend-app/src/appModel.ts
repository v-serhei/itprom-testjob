export interface ProfessionModel {
    id: null,
    name: string,
    note: string
}

export interface DepartmentModel {
    id: number,
    name: string,
    note: string,
    parentDepartmentId: number | null,
    children?: DepartmentModel[]
}

export interface EmployeeModel {
    id: null,
    firstName: string,
    lastName: string,
    middleName: string,
    profession: ProfessionModel,
    department: DepartmentModel,
    note: string
}