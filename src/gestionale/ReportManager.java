package gestionale;

import java.util.ArrayList;
import java.util.List;

import gestionale.Report.GroupingType;
import gestionale.DAO.ProjectDAO;
import gestionale.helper.SystemHelper;

public class ReportManager {
	
	/**
	 * Print the report in a user chosen path
	 * @param order The order for the report
	 * @return true if it's saved, false if there was some error
	 */
	public static boolean printReport(Report report){
		String filePath = SystemHelper.getPathByUser();
		
		String toWrite = "Print this file and give it to the warehouseman that will give you the purchases.\n\n" +
				report.getInfo();
		
		return SystemHelper.writeFile(filePath, toWrite);
		
	}
	
	public static List<Report> list(User projectLeader, GroupingType groupBy){
		List<Report> list = new ArrayList<Report>();
		if (groupBy == GroupingType.PROJECT_BASE){
			List<Project> projects = ProjectDAO.listProjectsByProjectLeader(projectLeader);
			for(Project p : projects){
				Report r = new Report(p);
				if (!r.isEmpty())
					list.add(r);
			}
		}else{
			//Raggruppamento per utente
			list = Report.getByGroupingEmployees(projectLeader);
		}
		return list;
	}
}
