import React, { useState, useEffect } from 'react';
import { Calculator, Clock, CreditCard, Briefcase, Heart, DollarSign, Download, RefreshCw, ShieldCheck } from 'lucide-react';

// Simple currency formatter for Rials
const formatCurrency = (value) => {
    if (!value) return '0';
    return parseInt(value).toLocaleString('fa-IR');
};

// Simple parser for Rials
const parseCurrency = (value) => {
    if (!value) return 0;
    return parseInt(value.replace(/[^\d]/g, '')) || 0;
};

const App = () => {
    const [formData, setFormData] = useState({
        baseSalary: 150000000,
        overtimeHours: 12,
        attractionBonus: 5000000,
        specializationBonus: 8000000,
        marriageBonus: 12000000,
    });

    const [results, setResults] = useState({
        grossIncome: 0,
        overtimePay: 0,
        tax: 0,
        insurance: 0,
        netIncome: 0,
    });

    // Mock Calculation Logic
    useEffect(() => {
        const hourlyRate = formData.baseSalary / 192; // Approx 192 hours a month
        const overtimePay = Math.round(formData.overtimeHours * hourlyRate * 1.4); // 1.4x for overtime

        const grossIncome =
            formData.baseSalary +
            overtimePay +
            formData.attractionBonus +
            formData.specializationBonus +
            formData.marriageBonus;

        const insurance = Math.round(grossIncome * 0.07); // 7% Insurance
        const taxableIncome = grossIncome - insurance;
        const tax = taxableIncome > 100000000 ? Math.round((taxableIncome - 100000000) * 0.1) : 0; // Simple tax bracket logic

        const netIncome = grossIncome - insurance - tax;

        setResults({
            grossIncome,
            overtimePay,
            tax,
            insurance,
            netIncome
        });
    }, [formData]);

    const handleInputChange = (field, value) => {
        setFormData(prev => ({
            ...prev,
            [field]: value
        }));
    };

    return (
        <div className="min-h-screen bg-slate-900 font-['Vazirmatn'] text-right" dir="rtl">
            <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Vazirmatn:wght@100;300;400;500;700;900&display=swap');
        body { font-family: 'Vazirmatn', sans-serif; }
        /* Custom Scrollbar */
        ::-webkit-scrollbar { width: 8px; }
        ::-webkit-scrollbar-track { background: #0f172a; }
        ::-webkit-scrollbar-thumb { background: #334155; border-radius: 4px; }
        ::-webkit-scrollbar-thumb:hover { background: #475569; }
      `}</style>

            {/* Abstract Background Elements */}
            <div className="fixed inset-0 overflow-hidden pointer-events-none">
                <div className="absolute -top-[20%] -right-[10%] w-[50vw] h-[50vw] bg-indigo-600/20 rounded-full blur-[100px] animate-pulse"></div>
                <div className="absolute top-[40%] -left-[10%] w-[40vw] h-[40vw] bg-blue-500/10 rounded-full blur-[100px]"></div>
                <div className="absolute -bottom-[10%] right-[20%] w-[30vw] h-[30vw] bg-emerald-500/10 rounded-full blur-[100px]"></div>
            </div>

            <div className="relative z-10 container mx-auto px-4 py-8 lg:h-screen lg:flex lg:items-center lg:justify-center">

                <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 w-full max-w-7xl">

                    {/* LEFT COLUMN: The Receipt/Result (Visual) */}
                    <div className="lg:col-span-5 order-2 lg:order-1">
                        <div className="relative group">
                            {/* Glow Effect */}
                            <div className="absolute -inset-1 bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 rounded-2xl blur opacity-25 group-hover:opacity-50 transition duration-1000 group-hover:duration-200"></div>

                            <div className="relative bg-white text-slate-800 rounded-xl shadow-2xl overflow-hidden transform transition-all duration-500 hover:scale-[1.02]">
                                {/* Receipt Header */}
                                <div className="bg-slate-100 p-6 border-b border-dashed border-slate-300 flex justify-between items-center">
                                    <div className="flex items-center gap-3">
                                        <div className="p-2 bg-indigo-600 rounded-lg text-white">
                                            <Calculator size={24} />
                                        </div>
                                        <div>
                                            <h2 className="font-bold text-lg text-slate-800">فیش حقوقی هوشمند</h2>
                                            <p className="text-xs text-slate-500">پیش‌نمایش زنده محاسبات</p>
                                        </div>
                                    </div>
                                    <div className="text-left">
                                        <div className="text-xs font-mono text-slate-400">ID: #PAY-8821</div>
                                        <div className="text-xs text-emerald-600 font-bold mt-1 flex items-center gap-1 justify-end">
                                            <span className="w-2 h-2 bg-emerald-500 rounded-full animate-pulse"></span>
                                            فعال
                                        </div>
                                    </div>
                                </div>

                                {/* Receipt Body */}
                                <div className="p-6 space-y-4 relative">
                                    {/* Watermark */}
                                    <div className="absolute inset-0 flex items-center justify-center opacity-[0.03] pointer-events-none">
                                        <ShieldCheck size={200} />
                                    </div>

                                    {/* Income Section */}
                                    <div className="space-y-3">
                                        <div className="flex justify-between text-sm">
                                            <span className="text-slate-500">حقوق پایه</span>
                                            <span className="font-semibold text-slate-700">{formatCurrency(formData.baseSalary)} <span className="text-xs text-slate-400">ریال</span></span>
                                        </div>
                                        <div className="flex justify-between text-sm">
                                            <span className="text-slate-500">اضافه‌کاری ({formData.overtimeHours} ساعت)</span>
                                            <span className="font-semibold text-emerald-600">+{formatCurrency(results.overtimePay)} <span className="text-xs text-slate-400">ریال</span></span>
                                        </div>
                                        <div className="flex justify-between text-sm">
                                            <span className="text-slate-500">مزایا و پاداش‌ها</span>
                                            <span className="font-semibold text-emerald-600">+{formatCurrency(formData.attractionBonus + formData.specializationBonus + formData.marriageBonus)} <span className="text-xs text-slate-400">ریال</span></span>
                                        </div>
                                    </div>

                                    {/* Divider */}
                                    <div className="h-px bg-slate-200 my-4"></div>

                                    {/* Deductions */}
                                    <div className="space-y-3">
                                        <div className="flex justify-between text-sm">
                                            <span className="text-slate-500">حق بیمه (۷٪)</span>
                                            <span className="font-semibold text-red-500">-{formatCurrency(results.insurance)} <span className="text-xs text-slate-400">ریال</span></span>
                                        </div>
                                        <div className="flex justify-between text-sm">
                                            <span className="text-slate-500">مالیات بر درآمد</span>
                                            <span className="font-semibold text-red-500">-{formatCurrency(results.tax)} <span className="text-xs text-slate-400">ریال</span></span>
                                        </div>
                                    </div>

                                    {/* Total */}
                                    <div className="mt-8 pt-6 border-t-2 border-slate-800">
                                        <div className="flex justify-between items-end">
                                            <span className="text-slate-600 font-bold text-lg">خالص پرداختی</span>
                                            <div className="text-left">
                        <span className="block text-3xl font-black text-indigo-600 tracking-tight">
                          {formatCurrency(results.netIncome)}
                        </span>
                                                <span className="text-xs text-slate-400">ریال ایران</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* Receipt Footer */}
                                <div className="bg-slate-50 p-4 border-t border-slate-200 flex gap-2">
                                    <button className="flex-1 bg-slate-800 hover:bg-slate-900 text-white py-2 px-4 rounded-lg text-sm font-medium transition flex items-center justify-center gap-2">
                                        <Download size={16} />
                                        دانلود PDF
                                    </button>
                                    <button className="p-2 text-slate-500 hover:bg-slate-200 rounded-lg transition" onClick={() => setFormData({
                                        baseSalary: 0, overtimeHours: 0, attractionBonus: 0, specializationBonus: 0, marriageBonus: 0
                                    })}>
                                        <RefreshCw size={18} />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    {/* RIGHT COLUMN: Controls/Inputs */}
                    <div className="lg:col-span-7 order-1 lg:order-2 flex flex-col justify-center">
                        <div className="bg-slate-800/50 backdrop-blur-xl border border-slate-700/50 rounded-2xl p-8 text-white shadow-xl">

                            <div className="mb-8">
                                <h1 className="text-3xl font-bold mb-2 bg-clip-text text-transparent bg-gradient-to-r from-indigo-400 to-cyan-400">
                                    محاسبه‌گر حقوق
                                </h1>
                                <p className="text-slate-400 text-sm">
                                    اطلاعات فیش حقوقی را وارد کنید تا محاسبات به صورت خودکار انجام شود.
                                </p>
                            </div>

                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                                {/* Base Salary */}
                                <div className="md:col-span-2 space-y-2">
                                    <label className="flex items-center gap-2 text-sm font-medium text-slate-300">
                                        <CreditCard size={16} className="text-indigo-400"/>
                                        حقوق پایه ماهانه (ریال)
                                    </label>
                                    <div className="relative group">
                                        <input
                                            type="text"
                                            value={formatCurrency(formData.baseSalary)}
                                            onChange={(e) => handleInputChange('baseSalary', parseCurrency(e.target.value))}
                                            className="w-full bg-slate-900/80 border border-slate-700 text-white rounded-xl py-4 px-4 pl-12 focus:outline-none focus:border-indigo-500 focus:ring-2 focus:ring-indigo-500/20 transition font-mono text-lg"
                                            placeholder="0"
                                        />
                                        <div className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 text-sm">IRR</div>
                                    </div>
                                </div>

                                {/* Overtime */}
                                <div className="space-y-2">
                                    <label className="flex items-center gap-2 text-sm font-medium text-slate-300">
                                        <Clock size={16} className="text-orange-400"/>
                                        ساعات اضافه‌کاری
                                    </label>
                                    <div className="bg-slate-900/80 border border-slate-700 rounded-xl p-2 flex items-center gap-4">
                                        <input
                                            type="range"
                                            min="0"
                                            max="100"
                                            value={formData.overtimeHours}
                                            onChange={(e) => handleInputChange('overtimeHours', parseInt(e.target.value))}
                                            className="flex-1 h-2 bg-slate-700 rounded-lg appearance-none cursor-pointer accent-indigo-500"
                                        />
                                        <div className="w-16 h-10 bg-slate-800 rounded-lg flex items-center justify-center border border-slate-600 text-indigo-300 font-mono font-bold">
                                            {formData.overtimeHours}
                                        </div>
                                    </div>
                                </div>

                                {/* Attraction Bonus */}
                                <div className="space-y-2">
                                    <label className="flex items-center gap-2 text-sm font-medium text-slate-300">
                                        <DollarSign size={16} className="text-emerald-400"/>
                                        حق جذب (ریال)
                                    </label>
                                    <input
                                        type="text"
                                        value={formatCurrency(formData.attractionBonus)}
                                        onChange={(e) => handleInputChange('attractionBonus', parseCurrency(e.target.value))}
                                        className="w-full bg-slate-900/80 border border-slate-700 text-white rounded-xl py-3 px-4 focus:outline-none focus:border-emerald-500 focus:ring-1 focus:ring-emerald-500/20 transition font-mono"
                                    />
                                </div>

                                {/* Specialization Bonus */}
                                <div className="space-y-2">
                                    <label className="flex items-center gap-2 text-sm font-medium text-slate-300">
                                        <Briefcase size={16} className="text-blue-400"/>
                                        فوق العاده تخصص (ریال)
                                    </label>
                                    <input
                                        type="text"
                                        value={formatCurrency(formData.specializationBonus)}
                                        onChange={(e) => handleInputChange('specializationBonus', parseCurrency(e.target.value))}
                                        className="w-full bg-slate-900/80 border border-slate-700 text-white rounded-xl py-3 px-4 focus:outline-none focus:border-blue-500 focus:ring-1 focus:ring-blue-500/20 transition font-mono"
                                    />
                                </div>

                                {/* Marriage Bonus */}
                                <div className="space-y-2">
                                    <label className="flex items-center gap-2 text-sm font-medium text-slate-300">
                                        <Heart size={16} className="text-pink-400"/>
                                        حق تاهل (ریال)
                                    </label>
                                    <input
                                        type="text"
                                        value={formatCurrency(formData.marriageBonus)}
                                        onChange={(e) => handleInputChange('marriageBonus', parseCurrency(e.target.value))}
                                        className="w-full bg-slate-900/80 border border-slate-700 text-white rounded-xl py-3 px-4 focus:outline-none focus:border-pink-500 focus:ring-1 focus:ring-pink-500/20 transition font-mono"
                                    />
                                </div>

                            </div>

                            {/* Mobile Calculate Button (Visual only since real-time) */}
                            <div className="mt-8 lg:hidden">
                                <button className="w-full bg-indigo-600 text-white py-4 rounded-xl font-bold text-lg shadow-lg shadow-indigo-500/30 active:scale-95 transition">
                                    مشاهده جزئیات
                                </button>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default App;