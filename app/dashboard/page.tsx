"use client"

import { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Badge } from "@/components/ui/badge"
import { Alert, AlertDescription } from "@/components/ui/alert"
import {
  Anchor,
  Cloud,
  Droplets,
  Fuel,
  UtensilsCrossed,
  AlertTriangle,
  CheckCircle,
  History,
  LogOut,
} from "lucide-react"
import { useRouter } from "next/navigation"
import Link from "next/link"

interface WeatherData {
  temperature: number
  windSpeed: number
  waveHeight: number
  visibility: number
  condition: string
  humidity: number
}

interface TripCalculation {
  food: number
  water: number
  fuel: number
  isSafe: boolean
  safetyScore: number
  warnings: string[]
}

export default function DashboardPage() {
  const [tripData, setTripData] = useState({
    duration: "",
    crewSize: "",
    distance: "",
    location: "",
  })
  const [weather, setWeather] = useState<WeatherData | null>(null)
  const [calculation, setCalculation] = useState<TripCalculation | null>(null)
  const [loading, setLoading] = useState(false)
  const [profile, setProfile] = useState<any>(null)
  const router = useRouter()

  useEffect(() => {
    // Check authentication
    const isLoggedIn = localStorage.getItem("fisherman_logged_in")
    if (!isLoggedIn) {
      router.push("/login")
      return
    }

    // Load profile
    const savedProfile = localStorage.getItem("fisherman_profile")
    if (savedProfile) {
      const profileData = JSON.parse(savedProfile)
      setProfile(profileData)
      setTripData((prev) => ({ ...prev, crewSize: profileData.crewSize || "" }))
    }
  }, [router])

  const fetchWeatherData = async (location: string): Promise<WeatherData> => {
    // Simulate weather API call with realistic data
    await new Promise((resolve) => setTimeout(resolve, 1000))

    const conditions = ["Clear", "Partly Cloudy", "Cloudy", "Light Rain", "Heavy Rain", "Stormy"]
    const randomCondition = conditions[Math.floor(Math.random() * conditions.length)]

    return {
      temperature: Math.floor(Math.random() * 20) + 70, // 70-90°F
      windSpeed: Math.floor(Math.random() * 25) + 5, // 5-30 mph
      waveHeight: Math.floor(Math.random() * 8) + 1, // 1-8 feet
      visibility: Math.floor(Math.random() * 5) + 5, // 5-10 miles
      condition: randomCondition,
      humidity: Math.floor(Math.random() * 40) + 40, // 40-80%
    }
  }

  const calculateTrip = async () => {
    if (!tripData.duration || !tripData.crewSize || !tripData.distance || !tripData.location) {
      alert("Please fill in all trip details")
      return
    }

    setLoading(true)

    try {
      // Fetch weather data
      const weatherData = await fetchWeatherData(tripData.location)
      setWeather(weatherData)

      // Calculate resources needed
      const duration = Number.parseInt(tripData.duration)
      const crew = Number.parseInt(tripData.crewSize)
      const distance = Number.parseInt(tripData.distance)

      // Food calculation (kg per person per day)
      const foodPerPersonPerDay = 1.5
      const totalFood = duration * crew * foodPerPersonPerDay

      // Water calculation (liters per person per day)
      const waterPerPersonPerDay = 4
      const totalWater = duration * crew * waterPerPersonPerDay

      // Fuel calculation (gallons based on distance and boat type)
      const fuelEfficiency = profile?.boatType === "small-boat" ? 4 : profile?.boatType === "medium-boat" ? 2.5 : 1.5
      const totalFuel = (distance * 2) / fuelEfficiency // Round trip

      // Safety assessment
      const warnings: string[] = []
      let safetyScore = 100

      if (weatherData.windSpeed > 20) {
        warnings.push("High wind speeds detected")
        safetyScore -= 30
      }
      if (weatherData.waveHeight > 6) {
        warnings.push("High wave conditions")
        safetyScore -= 25
      }
      if (weatherData.visibility < 7) {
        warnings.push("Poor visibility conditions")
        safetyScore -= 20
      }
      if (weatherData.condition.includes("Rain") || weatherData.condition === "Stormy") {
        warnings.push("Adverse weather conditions")
        safetyScore -= 20
      }

      const isSafe = safetyScore >= 70

      const tripCalculation: TripCalculation = {
        food: Math.ceil(totalFood),
        water: Math.ceil(totalWater),
        fuel: Math.ceil(totalFuel),
        isSafe,
        safetyScore,
        warnings,
      }

      setCalculation(tripCalculation)

      // Save to history if safe
      if (isSafe) {
        const historyEntry = {
          id: Date.now(),
          date: new Date().toISOString(),
          location: tripData.location,
          duration: duration,
          crew: crew,
          distance: distance,
          weather: weatherData,
          calculation: tripCalculation,
        }

        const existingHistory = JSON.parse(localStorage.getItem("trip_history") || "[]")
        existingHistory.push(historyEntry)
        localStorage.setItem("trip_history", JSON.stringify(existingHistory))
      }
    } catch (error) {
      console.error("Error calculating trip:", error)
      alert("Error calculating trip. Please try again.")
    } finally {
      setLoading(false)
    }
  }

  const handleLogout = () => {
    localStorage.removeItem("fisherman_logged_in")
    localStorage.removeItem("fisherman_email")
    localStorage.removeItem("fisherman_name")
    router.push("/login")
  }

  if (!profile) {
    return <div className="min-h-screen flex items-center justify-center">Loading...</div>
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-cyan-100">
      {/* Header */}
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center space-x-3">
              <Anchor className="h-8 w-8 text-blue-600" />
              <h1 className="text-2xl font-bold text-gray-900">Fisherman's Weather App</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-gray-600">Welcome, {profile.name}</span>
              <Link href="/history">
                <Button variant="outline" size="sm">
                  <History className="h-4 w-4 mr-2" />
                  History
                </Button>
              </Link>
              <Button variant="outline" size="sm" onClick={handleLogout}>
                <LogOut className="h-4 w-4 mr-2" />
                Logout
              </Button>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Trip Planning */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center">
                <Anchor className="h-5 w-5 mr-2" />
                Plan Your Fishing Trip
              </CardTitle>
              <CardDescription>
                Enter your trip details to get safety assessment and resource calculations
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="duration">Duration (days)</Label>
                  <Input
                    id="duration"
                    type="number"
                    placeholder="3"
                    value={tripData.duration}
                    onChange={(e) => setTripData({ ...tripData, duration: e.target.value })}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="crewSize">Crew Size</Label>
                  <Input
                    id="crewSize"
                    type="number"
                    placeholder="4"
                    value={tripData.crewSize}
                    onChange={(e) => setTripData({ ...tripData, crewSize: e.target.value })}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="distance">Distance (nautical miles)</Label>
                <Input
                  id="distance"
                  type="number"
                  placeholder="50"
                  value={tripData.distance}
                  onChange={(e) => setTripData({ ...tripData, distance: e.target.value })}
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="location">Fishing Location</Label>
                <Select
                  value={tripData.location}
                  onValueChange={(value) => setTripData({ ...tripData, location: value })}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Select location" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="gulf-of-mexico">Gulf of Mexico</SelectItem>
                    <SelectItem value="atlantic-coast">Atlantic Coast</SelectItem>
                    <SelectItem value="pacific-coast">Pacific Coast</SelectItem>
                    <SelectItem value="great-lakes">Great Lakes</SelectItem>
                    <SelectItem value="caribbean">Caribbean</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <Button onClick={calculateTrip} className="w-full bg-blue-600 hover:bg-blue-700" disabled={loading}>
                {loading ? "Calculating..." : "Calculate Trip Requirements"}
              </Button>
            </CardContent>
          </Card>

          {/* Results */}
          <div className="space-y-6">
            {/* Weather Conditions */}
            {weather && (
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center">
                    <Cloud className="h-5 w-5 mr-2" />
                    Current Weather Conditions
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-2 gap-4 text-sm">
                    <div className="flex justify-between">
                      <span>Temperature:</span>
                      <span className="font-medium">{weather.temperature}°F</span>
                    </div>
                    <div className="flex justify-between">
                      <span>Wind Speed:</span>
                      <span className="font-medium">{weather.windSpeed} mph</span>
                    </div>
                    <div className="flex justify-between">
                      <span>Wave Height:</span>
                      <span className="font-medium">{weather.waveHeight} ft</span>
                    </div>
                    <div className="flex justify-between">
                      <span>Visibility:</span>
                      <span className="font-medium">{weather.visibility} miles</span>
                    </div>
                    <div className="flex justify-between col-span-2">
                      <span>Condition:</span>
                      <span className="font-medium">{weather.condition}</span>
                    </div>
                  </div>
                </CardContent>
              </Card>
            )}

            {/* Safety Assessment */}
            {calculation && (
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center">
                    {calculation.isSafe ? (
                      <CheckCircle className="h-5 w-5 mr-2 text-green-600" />
                    ) : (
                      <AlertTriangle className="h-5 w-5 mr-2 text-red-600" />
                    )}
                    Trip Safety Assessment
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4">
                    <div className="flex items-center justify-between">
                      <span>Safety Score:</span>
                      <Badge variant={calculation.isSafe ? "default" : "destructive"}>
                        {calculation.safetyScore}/100
                      </Badge>
                    </div>

                    <Alert variant={calculation.isSafe ? "default" : "destructive"}>
                      <AlertDescription>
                        {calculation.isSafe
                          ? "✅ This trip is SAFE to proceed based on current conditions."
                          : "⚠️ This trip is NOT SAFE. Consider postponing or changing plans."}
                      </AlertDescription>
                    </Alert>

                    {calculation.warnings.length > 0 && (
                      <div>
                        <h4 className="font-medium text-sm mb-2">Warnings:</h4>
                        <ul className="text-sm space-y-1">
                          {calculation.warnings.map((warning, index) => (
                            <li key={index} className="flex items-center text-amber-700">
                              <AlertTriangle className="h-3 w-3 mr-2" />
                              {warning}
                            </li>
                          ))}
                        </ul>
                      </div>
                    )}
                  </div>
                </CardContent>
              </Card>
            )}

            {/* Resource Requirements */}
            {calculation && (
              <Card>
                <CardHeader>
                  <CardTitle>Required Resources</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4">
                    <div className="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                      <div className="flex items-center">
                        <UtensilsCrossed className="h-5 w-5 mr-3 text-green-600" />
                        <span>Food Required</span>
                      </div>
                      <span className="font-bold text-green-700">{calculation.food} kg</span>
                    </div>

                    <div className="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                      <div className="flex items-center">
                        <Droplets className="h-5 w-5 mr-3 text-blue-600" />
                        <span>Water Required</span>
                      </div>
                      <span className="font-bold text-blue-700">{calculation.water} liters</span>
                    </div>

                    <div className="flex items-center justify-between p-3 bg-orange-50 rounded-lg">
                      <div className="flex items-center">
                        <Fuel className="h-5 w-5 mr-3 text-orange-600" />
                        <span>Fuel Required</span>
                      </div>
                      <span className="font-bold text-orange-700">{calculation.fuel} gallons</span>
                    </div>
                  </div>
                </CardContent>
              </Card>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
