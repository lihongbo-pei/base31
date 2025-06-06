package com.adtec.rdc.base.monitor.agent.bo;

/**
 * CPU相关信息
 * 
 * @author JTao
 */
public class Cpu implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8045741488673264790L;

	/**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum()
    {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum)
    {
        this.cpuNum = cpuNum;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getSys()
    {
        return sys;
    }

    public void setSys(double sys)
    {
        this.sys = sys;
    }

    public double getUsed()
    {
        return used;
    }

    public void setUsed(double used)
    {
        this.used = used;
    }

    public double getWait()
    {
        return wait;
    }

    public void setWait(double wait)
    {
        this.wait = wait;
    }

    public double getFree()
    {
        return free;
    }

    public void setFree(double free)
    {
        this.free = free;
    }

	@Override
	public String toString() {
		return "Cpu [cpuNum=" + cpuNum + ", total=" + total + ", sys=" + sys + ", used=" + used + ", wait=" + wait
				+ ", free=" + free + "]";
	}
    
}
